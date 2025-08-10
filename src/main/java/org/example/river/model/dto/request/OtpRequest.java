package org.example.river.model.dto.request;

import jakarta.validation.constraints.NotBlank;

public record OtpRequest(
        @NotBlank
        String email
) {
}
