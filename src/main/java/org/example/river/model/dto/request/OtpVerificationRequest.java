package org.example.river.model.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public record OtpVerificationRequest(
        @NotBlank
        @Email
        String email,

        @NotBlank
        @Length(min = 6, max = 6)
        String otp
) {
}
