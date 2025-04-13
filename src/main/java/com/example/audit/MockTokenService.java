package com.example.audit;

import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class MockTokenService {
    private final Map<String, User> tokenUserMap = new ConcurrentHashMap<>();

    public MockTokenService() {
        // 預設模擬數據
        tokenUserMap.put("token1", new User(1L, "admin", "Company A", "IT"));
        tokenUserMap.put("token2", new User(2L, "user", "Company B", "HR"));
    }

    public User getUserFromToken(String token) {
        return tokenUserMap.get(token);
    }
} 