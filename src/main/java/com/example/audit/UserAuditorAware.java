package com.example.audit;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.hibernate.Session;

import java.util.Optional;

@Component
public class UserAuditorAware implements AuditorAware<UserRef> {
    @PersistenceContext
    private EntityManager entityManager;
    
    // 定義硬編碼的用戶引用
    private static final UserRef ADMIN_USER = new UserRef(1L, "admin", "Admin User");
    private static final UserRef NORMAL_USER = new UserRef(2L, "user", "Normal User");

    // 使用 ThreadLocal 變量來存儲當前用戶的公司、單位和語言信息
    private static final ThreadLocal<String> currentCompany = ThreadLocal.withInitial(() -> "DEFAULT_COMPANY");
    private static final ThreadLocal<String> currentUnit = ThreadLocal.withInitial(() -> "DEFAULT_UNIT");
    private static final ThreadLocal<String> currentLanguage = ThreadLocal.withInitial(() -> "zh_TW");
    private static final ThreadLocal<String> currentToken = ThreadLocal.withInitial(() -> "");

    @Override
    public Optional<UserRef> getCurrentAuditor() {
        // 根據模擬的 token 返回不同的用戶引用，並設置當前的公司和部門
        String token = getCurrentToken();
        UserRef userRef;
        if ("token1".equals(token)) {
            userRef = new UserRef(ADMIN_USER.getId(), ADMIN_USER.getUsername(), 
                ADMIN_USER.getDisplayName(), getCurrentCompany(), getCurrentUnit());
        } else {
            userRef = new UserRef(NORMAL_USER.getId(), NORMAL_USER.getUsername(), 
                NORMAL_USER.getDisplayName(), getCurrentCompany(), getCurrentUnit());
        }
        return Optional.of(userRef);
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