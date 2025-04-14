package com.example.audit;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

/**
 * 可嵌入的創建審計資訊類
 * 用於組合模式審計機制
 */
@Embeddable
@Getter
@Setter
public class CreateAuditInfo {
    
    // 標準審計欄位 - 創建者
    @CreatedBy
    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "created_by", updatable = false)),
        @AttributeOverride(name = "name", column = @Column(name = "created_name", updatable = false)),
        @AttributeOverride(name = "username", column = @Column(name = "created_username", updatable = false)),
        @AttributeOverride(name = "displayName", column = @Column(name = "created_display_name", updatable = false)),
        @AttributeOverride(name = "company", column = @Column(name = "created_company", updatable = false)),
        @AttributeOverride(name = "unit", column = @Column(name = "created_unit", updatable = false))
    })
    private User creator;

    // 標準審計欄位 - 創建時間
    @CreatedDate
    @Column(name = "created_time", nullable = false, updatable = false, precision = 6)
    private LocalDateTime createdTime;

    @Column(name = "default_language")
    private String defaultLanguage;


} 