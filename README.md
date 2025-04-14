# Spring Data JPA 審計功能開發範本

這個專案提供了一個完整的 Spring Data JPA 審計功能實現範例，旨在為開發人員提供一個可參考的審計機制範本。通過這個範本，開發人員可以快速實現系統中的審計追蹤功能，記錄數據的創建者和修改者資訊。

## 功能特點

- **完整的審計追蹤**：自動記錄數據的創建者、創建時間、修改者和修改時間
- **多樣化的審計模式**：提供標準審計（含創建和修改）和簡化審計（僅創建）兩種模式
- **自定義審計欄位**：除了標準審計欄位外，還支持自定義欄位如公司、部門等
- **無侵入式設計**：審計功能完全基於 JPA 註解和實體繼承，對業務代碼無侵入
- **線程安全**：基於 ThreadLocal 實現的用戶上下文管理，確保多線程環境下的安全性
- **與安全機制整合**：通過請求過濾器與認證機制整合，支持 Token 驗證

## 核心組件

### 審計基礎類

1. **AuditBase**：包含完整審計欄位（創建者和修改者）的基礎類
   - 記錄創建者、創建時間、修改者、修改時間等所有審計資訊
   - 適用於需要完整審計的業務實體

2. **CreateAuditBase**：僅包含創建審計欄位的基礎類
   - 僅記錄創建者和創建時間相關資訊
   - 適用於日誌、歷史記錄等只需記錄創建資訊的情境

### 用戶審計提供者

- **UserAuditorAware**：實現 AuditorAware 接口，提供當前操作用戶
  - 通過 ThreadLocal 維護當前請求的用戶資訊
  - 支持基於 Token 的用戶識別

### 過濾器

- **TokenFilter**：從 HTTP 請求中提取 Token，設置當前用戶上下文
- **ThreadLocalCleanupFilter**：確保請求結束後清理 ThreadLocal 變量，避免內存洩漏

## 使用指南

### 1. 創建實體類

從相應的審計基礎類繼承來獲取審計功能：

```java
// 需要完整審計（創建和修改）的實體
@Entity
@Table(name = "customer")
public class Customer extends AuditBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    // 業務屬性...
}

// 僅需要創建審計的實體（如日誌）
@Entity
@Table(name = "simple_log")
public class SimpleLog extends CreateAuditBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    // 業務屬性...
}
```

### 2. 數據庫表結構

確保表結構包含相應的審計欄位：

```sql
-- 完整審計表（包含創建和修改）
CREATE TABLE your_table (
    id bigserial primary key,
    -- 業務欄位...
    
    -- 審計欄位
    created_by bigint not null,
    created_time timestamp not null,
    created_name varchar(100),
    created_username varchar(100),
    created_display_name varchar(100),
    created_company varchar(100),
    created_unit varchar(100),
    
    modified_by bigint not null,
    modified_time timestamp not null,
    modified_name varchar(100),
    modified_username varchar(100),
    modified_display_name varchar(100),
    modified_company varchar(100),
    modified_unit varchar(100),
    
    default_language varchar(20)
);

-- 簡化審計表（僅創建欄位）
CREATE TABLE your_log_table (
    id bigserial primary key,
    -- 業務欄位...
    
    -- 審計欄位（僅創建者）
    created_by bigint not null,
    created_time timestamp not null,
    created_name varchar(100),
    created_username varchar(100),
    created_display_name varchar(100),
    created_company varchar(100),
    created_unit varchar(100),
    
    default_language varchar(20)
);
```

### 3. API 操作時的 Token 傳遞

在 API 調用時附加 Token 即可自動記錄審計資訊：

```bash
# 添加數據（自動記錄創建者資訊）
curl -X POST http://localhost:8080/api/customers \
-H "Content-Type: application/json" \
-H "X-Auth-Token: user1_token" \
-d '{"customerName":"測試客戶","jobTitle":"專案經理"}'

# 修改數據（自動記錄修改者資訊）
curl -X PUT http://localhost:8080/api/customers/1 \
-H "Content-Type: application/json" \
-H "X-Auth-Token: user2_token" \
-d '{"customerName":"測試客戶-已更新","jobTitle":"高級專案經理"}'
```

## 示例說明

本專案包含兩個完整的示例：

1. **Customer**：完整審計（繼承自 AuditBase）
   - 完整的 CRUD 操作
   - 展示創建者和修改者資訊的變化

2. **SimpleLog**：簡化審計（繼承自 CreateAuditBase）
   - 僅記錄創建者資訊
   - 適用於日誌、歷史記錄等不需追蹤修改的場景

## 配置與擴展

### 自定義審計用戶

修改 UserAuditorAware 類可以配置不同的用戶識別邏輯：

```java
@Component
public class UserAuditorAware implements AuditorAware<User> {
    // 自定義用戶識別邏輯
    @Override
    public Optional<User> getCurrentAuditor() {
        // 例如集成 Spring Security、OAuth2 等
        // 返回當前認證用戶
    }
}
```

### 集成其他認證機制

如果使用不同的認證機制，可修改 TokenFilter 獲取當前用戶：

```java
@Component
public class TokenFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, ...) {
        // 從 Spring Security 上下文獲取用戶
        // 或從 OAuth2 Token 中獲取用戶
        // 設置到 UserAuditorAware
    }
}
```

## 啟動與測試

### 項目啟動

```bash
# 編譯並啟動
mvn clean install
mvn spring-boot:run

# 或使用 jar 檔案啟動
java -jar target/spring-data-jpa-auditing-demo.jar
```

### 測試審計功能

以下是具體的測試案例，展示了完整和簡化審計功能：

#### Customer 實體測試 (完整審計)

```bash
# 使用 user1 建立客戶
curl -X POST http://localhost:8080/api/customers \
-H "Content-Type: application/json" \
-H "X-Auth-Token: user1_token" \
-d '{"customerName":"測試客戶A","jobTitle":"專案經理"}'

# 使用 user2 更新客戶
curl -X PUT http://localhost:8080/api/customers/18 \
-H "Content-Type: application/json" \
-H "X-Auth-Token: user2_token" \
-d '{"customerName":"測試客戶A-更新","jobTitle":"資深專案經理"}'
```

#### SimpleLog 實體測試 (簡化審計，只有創建欄位)

```bash
# 使用 user1 建立日誌
curl -X POST http://localhost:8080/api/logs \
-H "Content-Type: application/json" \
-H "X-Auth-Token: user1_token" \
-d '{"eventType":"API_CALL","message":"測試API調用","eventTime":"2025-04-14T11:30:00"}'

# 嘗試使用 user2 更新日誌內容（僅業務資料會更新，審計欄位不變）
curl -X PUT http://localhost:8080/api/logs/1 \
-H "Content-Type: application/json" \
-H "X-Auth-Token: user2_token" \
-d '{"eventType":"SYSTEM_UPDATE","message":"系統更新完成123","eventTime":"2025-04-14T12:00:00"}'
```

## 注意事項

- 確保數據庫表結構包含所有必要的審計欄位
- 確保在請求中提供正確的 Token
- 對於高併發場景，需注意 ThreadLocal 的使用
- 定期檢查審計記錄的完整性

---

這個範本提供了一個靈活且可擴展的審計機制，可以根據實際需求進行調整和擴展。通過簡單的繼承，您可以為任何實體類增加審計能力，無需編寫額外的審計代碼。 