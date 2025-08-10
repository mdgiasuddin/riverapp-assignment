package org.example.river.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.river.repository.UserRepository;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.HashSet;

import static org.example.river.constants.AppConstant.TOKEN_TYPE;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        final String authHeader = request.getHeader(AUTHORIZATION);
        final String requestUri = request.getRequestURI();

        if (authHeader == null || !authHeader.startsWith(TOKEN_TYPE)) {
            filterChain.doFilter(request, response);
            return;
        }

        final String accessToken = authHeader.substring(TOKEN_TYPE.length());
        String email = jwtService.extractEmail(accessToken);

        if (!requestUri.equals("/api/users")) {
            userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User with email " + email + " not found"));
        }
        if (jwtService.isTokenValid(accessToken)) {
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    email,
                    null,
                    new HashSet<>()
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);

    }
}
