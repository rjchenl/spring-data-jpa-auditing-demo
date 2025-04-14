package com.example.audit;

import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserAuditorAware implements AuditorAware<User> {

    

    private static final User ADMIN_USER = new User(1L, "admin", "Admin User");
    private static final User NORMAL_USER = new User(2L, "user", "Normal User");

    private static final ThreadLocal<String> currentCompany = ThreadLocal.withInitial(() -> "DEFAULT_COMPANY");
    private static final ThreadLocal<String> currentUnit = ThreadLocal.withInitial(() -> "DEFAULT_UNIT");
    private static final ThreadLocal<String> currentLanguage = ThreadLocal.withInitial(() -> "zh_TW");
    private static final ThreadLocal<String> currentToken = ThreadLocal.withInitial(() -> "");

    @Override
    public Optional<User> getCurrentAuditor() {
        // 模擬jwt
        String token = getCurrentToken();
        User user;
        // 簡化判斷邏輯，只使用 admin_token
        if ("admin_token".equals(token)) {
            user = new User(ADMIN_USER.getId(), ADMIN_USER.getUsername(),
                ADMIN_USER.getDisplayName(), getCurrentCompany(), getCurrentUnit());
            user.setName(ADMIN_USER.getDisplayName());  // 設置 name 欄位
        } else {
            user = new User(NORMAL_USER.getId(), NORMAL_USER.getUsername(),
                NORMAL_USER.getDisplayName(), getCurrentCompany(), getCurrentUnit());
            user.setName(NORMAL_USER.getDisplayName());  // 設置 name 欄位
        }
        return Optional.of(user);
    }

    // 模擬設置 token 的方法，在實際應用中，這可能由攔截器或過濾器設置
    public static void setCurrentToken(String token) {
        currentToken.set(token);
    }

    public static String getCurrentToken() {
        return currentToken.get();
    }

    // 提供設置當前用戶公司、單位和語言的公共方法
    public static void setCurrentCompany(String company) {
        currentCompany.set(company);
    }

    public static String getCurrentCompany() {
        return currentCompany.get();
    }

    public static void setCurrentUnit(String unit) {
        currentUnit.set(unit);
    }

    public static String getCurrentUnit() {
        return currentUnit.get();
    }

    public static void setCurrentLanguage(String language) {
        currentLanguage.set(language);
    }

    public static String getCurrentLanguage() {
        return currentLanguage.get();
    }

    // 非靜態方法，用於在過濾器中調用
    public void clear() {
        clearAll();
    }

    // 清理 ThreadLocal 變量，防止記憶體洩漏
    public static void clearAll() {
        currentToken.remove();
        currentCompany.remove();
        currentUnit.remove();
        currentLanguage.remove();
    }
}