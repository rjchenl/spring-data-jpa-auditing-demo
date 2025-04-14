package com.example.config;

import com.example.audit.UserAuditorAware;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class TokenFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            String token = request.getHeader("X-Auth-Token");
            if (token != null) {
                UserAuditorAware.setCurrentToken(token);
            }
            filterChain.doFilter(request, response);
        } finally {
            // 不需要在這裡清理 ThreadLocal，因為 AuditConfig 中的過濾器會處理
        }
    }
} 