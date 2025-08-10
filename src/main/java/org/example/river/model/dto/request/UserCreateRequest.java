package org.example.river.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import static org.example.river.constants.AppConstant.MOBILE_NUMBER_REGEX;

public record UserCreateRequest(

        @NotBlank
        String firstName,

        @NotBlank
        String lastName,

        @NotBlank
        @Pattern(regexp = MOBILE_NUMBER_REGEX)
        String phoneNumber,

        @NotBlank
        String designation,

        @NotBlank
        String company,

        @NotBlank
        String address
) {
}
