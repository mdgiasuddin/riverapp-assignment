package org.example.river.utils;

import lombok.NoArgsConstructor;

import java.security.SecureRandom;
import java.util.Random;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class RandomStringUtils {
    private static final String DIGITS = "0123456789";
    private static final Random RANDOM = new SecureRandom();

    public static String generateRandomString(int length) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(DIGITS.charAt(RANDOM.nextInt(DIGITS.length())));
        }

        return sb.toString();
    }
}
