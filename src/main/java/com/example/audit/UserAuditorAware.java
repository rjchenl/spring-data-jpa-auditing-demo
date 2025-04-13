package com.example.audit;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Optional;

@Component
public class UserAuditorAware implements AuditorAware<User> {
    private final MockTokenService mockTokenService;

    public UserAuditorAware(MockTokenService mockTokenService) {
        this.mockTokenService = mockTokenService;
    }

    @Override
    public Optional<User> getCurrentAuditor() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            HttpServletRequest request = attributes.getRequest();
            String token = request.getHeader("Authorization");
            if (token != null) {
                User user = mockTokenService.getUserFromToken(token);
                return Optional.ofNullable(user);
            }
        }
        return Optional.empty();
    }
} 