package org.example.river.utils;

import lombok.NoArgsConstructor;
import org.example.river.exception.ResourceNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class SecurityUtils {

    public static String getUserEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new ResourceNotFoundException("USER_NOT_LOGGED_IN", "User Not Logged In");
        }

        return authentication.getName();
    }
}
