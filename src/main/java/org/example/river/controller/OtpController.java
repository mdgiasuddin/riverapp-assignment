package org.example.river.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.river.model.dto.request.OtpRequest;
import org.example.river.model.dto.request.OtpVerificationRequest;
import org.example.river.model.dto.response.AuthenticationResponse;
import org.example.river.service.base.OtpService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/otp")
@RequiredArgsConstructor
public class OtpController {
    private final OtpService otpService;

    @ResponseStatus(CREATED)
    @PostMapping
    public String sendOtp(@Valid @RequestBody OtpRequest request) {
        otpService.sendOtp(request);
        return "OTP sent. Please check your email.";
    }

    @ResponseStatus(OK)
    @PostMapping("/verify")
    public AuthenticationResponse verifyOtp(@Valid @RequestBody OtpVerificationRequest request) {
        return otpService.verifyOtp(request);
    }
}
