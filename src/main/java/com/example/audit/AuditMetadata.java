package com.example.audit;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.AttributeOverride;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

/**
 * 包含所有審計信息的嵌入式類，包括標準和自定義欄位
 */
@Embeddable
@Getter
@Setter
public class AuditMetadata {
    // 標準審計欄位 - 創建者
    @CreatedBy
    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "created_by", updatable = false)),
        @AttributeOverride(name = "name", column = @Column(name = "created_name", updatable = false)),
        @AttributeOverride(name = "username", column = @Column(name = "created_username", updatable = false)),
        @AttributeOverride(name = "displayName", column = @Column(name = "created_display_name", updatable = false))
    })
    private UserRef creator;

    // 標準審計欄位 - 創建時間
    @CreatedDate
    @Column(name = "created_time", nullable = false, updatable = false, precision = 6)
    private LocalDateTime createdTime;

    // 標準審計欄位 - 修改者
    @LastModifiedBy
    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "modified_by")),
        @AttributeOverride(name = "name", column = @Column(name = "modified_name")),
        @AttributeOverride(name = "username", column = @Column(name = "modified_username")),
        @AttributeOverride(name = "displayName", column = @Column(name = "modified_display_name"))
    })
    private UserRef modifier;

    // 標準審計欄位 - 修改時間
    @LastModifiedDate
    @Column(name = "modified_time", nullable = false, precision = 6)
    private LocalDateTime modifiedTime;

    // 自定義審計欄位
    @Column(name = "created_company", updatable = false)
    private String createdCompany;

    @Column(name = "modified_company")
    private String modifiedCompany;

    @Column(name = "created_unit", updatable = false)
    private String createdUnit;

    @Column(name = "modified_unit")
    private String modifiedUnit;

    @Column(name = "default_language")
    private String defaultLanguage;
} 