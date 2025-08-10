package org.example.river.model.dto.response;

public record AuthenticationResponse(
        String accessToken,
        String refreshToken,
        String status
) {

}
