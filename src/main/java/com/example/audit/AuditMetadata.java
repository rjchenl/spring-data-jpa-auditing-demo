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
        @AttributeOverride(name = "displayName", column = @Column(name = "created_display_name", updatable = false)),
        @AttributeOverride(name = "company", column = @Column(name = "created_company", updatable = false)),
        @AttributeOverride(name = "unit", column = @Column(name = "created_unit", updatable = false))
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
        @AttributeOverride(name = "displayName", column = @Column(name = "modified_display_name")),
        @AttributeOverride(name = "company", column = @Column(name = "modified_company")),
        @AttributeOverride(name = "unit", column = @Column(name = "modified_unit"))
    })
    private UserRef modifier;

    // 標準審計欄位 - 修改時間
    @LastModifiedDate
    @Column(name = "modified_time", nullable = false, precision = 6)
    private LocalDateTime modifiedTime;

    @Column(name = "default_language")
    private String defaultLanguage;

    // Getter 和 Setter 方法
    public String getCreatedName() {
        return creator != null ? creator.getName() : null;
    }

    public void setCreatedName(String createdName) {
        if (creator == null) {
            creator = new UserRef();
        }
        creator.setName(createdName);
    }

    public String getModifiedName() {
        return modifier != null ? modifier.getName() : null;
    }

    public void setModifiedName(String modifiedName) {
        if (modifier == null) {
            modifier = new UserRef();
        }
        modifier.setName(modifiedName);
    }

    public String getCreatedCompany() {
        return creator != null ? creator.getCompany() : null;
    }

    public void setCreatedCompany(String createdCompany) {
        if (creator == null) {
            creator = new UserRef();
        }
        creator.setCompany(createdCompany);
    }

    public String getModifiedCompany() {
        return modifier != null ? modifier.getCompany() : null;
    }

    public void setModifiedCompany(String modifiedCompany) {
        if (modifier == null) {
            modifier = new UserRef();
        }
        modifier.setCompany(modifiedCompany);
    }

    public String getCreatedUnit() {
        return creator != null ? creator.getUnit() : null;
    }

    public void setCreatedUnit(String createdUnit) {
        if (creator == null) {
            creator = new UserRef();
        }
        creator.setUnit(createdUnit);
    }

    public String getModifiedUnit() {
        return modifier != null ? modifier.getUnit() : null;
    }

    public void setModifiedUnit(String modifiedUnit) {
        if (modifier == null) {
            modifier = new UserRef();
        }
        modifier.setUnit(modifiedUnit);
    }
} 