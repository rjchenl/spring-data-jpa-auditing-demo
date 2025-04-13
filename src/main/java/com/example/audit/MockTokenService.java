package com.example.audit;

import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class MockTokenService {
    private final Map<String, UserRef> tokenUserMap = new ConcurrentHashMap<>();

    public MockTokenService() {
        // 預設模擬數據
        tokenUserMap.put("token1", new UserRef(1L, "admin", "Admin User", "Company A", "IT"));
        tokenUserMap.put("token2", new UserRef(2L, "user", "Normal User", "Company B", "HR"));
    }

    public UserRef getUserFromToken(String token) {
        return tokenUserMap.get(token);
    }
} 