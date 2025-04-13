package com.example.audit;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class MockTokenService {
    private final Map<String, User> tokenUserMap = new HashMap<>();

    public MockTokenService() {
        // 預設模擬數據
        tokenUserMap.put("user1-token", new User(1L, "user1", "Company A", "Unit A"));
        tokenUserMap.put("user2-token", new User(2L, "user2", "Company B", "Unit B"));
    }

    public User getUserFromToken(String token) {
        return tokenUserMap.get(token);
    }
} 