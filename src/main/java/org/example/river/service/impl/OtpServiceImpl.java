package org.example.river.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.river.config.JwtService;
import org.example.river.exception.ResourceNotFoundException;
import org.example.river.exception.RulesViolationException;
import org.example.river.model.dto.request.OtpRequest;
import org.example.river.model.dto.request.OtpVerificationRequest;
import org.example.river.model.dto.response.AuthenticationResponse;
import org.example.river.model.entity.User;
import org.example.river.model.entity.UserOTP;
import org.example.river.repository.OtpRepository;
import org.example.river.service.base.OtpService;
import org.example.river.service.base.UserService;
import org.example.river.utils.RandomStringUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.example.river.constants.AppConstant.ACCESS_TOKEN_EXPIRY_MINUTE;
import static org.example.river.constants.AppConstant.OTP_EXPIRY_TIME;
import static org.example.river.constants.AppConstant.OTP_LENGTH;
import static org.example.river.constants.AppConstant.REFRESH_TOKEN_EXPIRY_DAY;
import static org.example.river.model.enumeration.OtpStatus.PENDING;
import static org.example.river.model.enumeration.OtpStatus.VERIFIED;

@Slf4j
@Service
@RequiredArgsConstructor
public class OtpServiceImpl implements OtpService {

    private final OtpRepository otpRepository;
    private final UserService userService;
    private final JwtService jwtService;

    @Override
    public void sendOtp(OtpRequest request) {
        Optional<UserOTP> otpOptional = otpRepository.findValidOtp(request.email(), LocalDateTime.now(), PENDING);
        if (otpOptional.isPresent()) {
            throw new RulesViolationException("OTP_ALREADY_EXIST", "Already sent an otp. Please retry after sometime.");
        }

        String otp = RandomStringUtils.generateRandomString(OTP_LENGTH);

        UserOTP userOTP = new UserOTP();
        userOTP.setOtp(otp);
        userOTP.setEmail(request.email());
        userOTP.setStatus(PENDING);
        userOTP.setExpiresAt(LocalDateTime.now().plusMinutes(OTP_EXPIRY_TIME));
        otpRepository.save(userOTP);
        log.info("Sending otp : {}", otp);
    }

    @Override
    public AuthenticationResponse verifyOtp(OtpVerificationRequest request) {
        UserOTP userOTP = otpRepository.findValidOtp(request.email(), LocalDateTime.now(), PENDING)
                .orElseThrow(() -> new ResourceNotFoundException("NO_VALID_OTP_FOUND", "Otp not found or expired."));

        if (!userOTP.getOtp().equals(request.otp())) {
            throw new RulesViolationException("OTP_DOES_NOT_MATCH", "Otp does not match.");
        }

        userOTP.setStatus(VERIFIED);
        otpRepository.save(userOTP);

        User user = userService.getUserByEmail(request.email());
        String status = "VERIFICATION_COMPLETED";

        if (user == null) {
            status = "USER_NOT_REGISTERED";
        }

        String accessToken = jwtService.generateToken(userOTP.getEmail(), ACCESS_TOKEN_EXPIRY_MINUTE * 60 * 1000);
        String refreshToken = jwtService.generateToken(userOTP.getEmail(), REFRESH_TOKEN_EXPIRY_DAY * 7 * 86400 * 1000);

        return new AuthenticationResponse(accessToken, refreshToken, status);
    }
}
