package com.example.audit;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.Embedded;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * 審計基礎類，使用嵌入式 AuditMetadata 包含所有審計信息
 */
@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class AuditBase {
    
    /**
     * 嵌入式審計信息，包含標準和自定義審計欄位
     */
    @Embedded
    private AuditMetadata auditMetadata = new AuditMetadata();
    
    // 為了保持與原有代碼的兼容性，提供委託方法
    
    // 自定義審計欄位的 Getter 和 Setter
    public String getCreatedCompany() {
        return auditMetadata.getCreatedCompany();
    }
    
    public void setCreatedCompany(String createdCompany) {
        auditMetadata.setCreatedCompany(createdCompany);
    }
    
    public String getModifiedCompany() {
        return auditMetadata.getModifiedCompany();
    }
    
    public void setModifiedCompany(String modifiedCompany) {
        auditMetadata.setModifiedCompany(modifiedCompany);
    }
    
    public String getCreatedUnit() {
        return auditMetadata.getCreatedUnit();
    }
    
    public void setCreatedUnit(String createdUnit) {
        auditMetadata.setCreatedUnit(createdUnit);
    }
    
    public String getModifiedUnit() {
        return auditMetadata.getModifiedUnit();
    }
    
    public void setModifiedUnit(String modifiedUnit) {
        auditMetadata.setModifiedUnit(modifiedUnit);
    }
    
    public String getDefaultLanguage() {
        return auditMetadata.getDefaultLanguage();
    }
    
    public void setDefaultLanguage(String defaultLanguage) {
        auditMetadata.setDefaultLanguage(defaultLanguage);
    }
}