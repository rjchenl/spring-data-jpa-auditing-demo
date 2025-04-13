package com.example.config;

import com.example.audit.UserAuditorAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import java.io.IOException;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "userAuditorAware")
public class AuditConfig {
    
    /**
     * 過濾器用於清理 ThreadLocal 變量，避免內存洩漏
     */
    @Bean
    public OncePerRequestFilter threadLocalCleanupFilter(@Autowired UserAuditorAware userAuditorAware) {
        return new OncePerRequestFilter() {
            @Override
            protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
                    throws ServletException, IOException {
                try {
                    filterChain.doFilter(request, response);
                } finally {
                    // 在請求處理完成後清理 ThreadLocal
                    userAuditorAware.clear();
                }
            }
        };
    }
} 