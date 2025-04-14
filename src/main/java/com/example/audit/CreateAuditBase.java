package com.example.audit;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.Embedded;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Column;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.AttributeOverride;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

/**
 * 僅創建審計基礎類，只包含創建相關的審計資訊
 */
@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class CreateAuditBase {
    
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

    public String getCreatedName() {
        return creator != null ? creator.getName() : null;
    }

    public void setCreatedName(String createdName) {
        if (creator == null) {
            creator = new User();
        }
        creator.setName(createdName);
    }

    public String getCreatedCompany() {
        return creator != null ? creator.getCompany() : null;
    }

    public void setCreatedCompany(String createdCompany) {
        if (creator == null) {
            creator = new User();
        }
        creator.setCompany(createdCompany);
    }

    public String getCreatedUnit() {
        return creator != null ? creator.getUnit() : null;
    }

    public void setCreatedUnit(String createdUnit) {
        if (creator == null) {
            creator = new User();
        }
        creator.setUnit(createdUnit);
    }
} 