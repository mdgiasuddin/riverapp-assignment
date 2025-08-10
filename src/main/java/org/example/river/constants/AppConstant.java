package org.example.river.constants;

import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class AppConstant {
    public static final int OTP_EXPIRY_TIME = 2;
    public static final int OTP_LENGTH = 6;
    public static final String MOBILE_NUMBER_REGEX = "^(01)(\\d{9})$";
    public static final String TOKEN_TYPE = "Bearer ";
    public static final Long ACCESS_TOKEN_EXPIRY_MINUTE = 30L;
    public static final Long REFRESH_TOKEN_EXPIRY_DAY = 7L;
}
