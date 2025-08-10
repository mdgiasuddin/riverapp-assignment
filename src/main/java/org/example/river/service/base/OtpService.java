package org.example.river.service.base;

import org.example.river.model.dto.request.OtpRequest;
import org.example.river.model.dto.request.OtpVerificationRequest;
import org.example.river.model.dto.response.AuthenticationResponse;

public interface OtpService {
    void sendOtp(OtpRequest request);

    AuthenticationResponse verifyOtp(OtpVerificationRequest request);
}
