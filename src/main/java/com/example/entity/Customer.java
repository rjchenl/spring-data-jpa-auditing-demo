package com.example.entity;

import com.example.audit.CreateAuditInfo;
import com.example.audit.UpdateAuditInfo;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "customer")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "customer_name", nullable = false)
    private String customerName;

    @Column(name = "job_title")
    private String jobTitle;
    
    // 嵌入創建審計資訊
    @Embedded
    private CreateAuditInfo createAudit = new CreateAuditInfo();
    
    // 嵌入修改審計資訊
    @Embedded
    private UpdateAuditInfo updateAudit = new UpdateAuditInfo();
    
    // 提供便捷方法，使用方式和原先相同
    
    // 創建者相關
    public String getCreatedName() {
        return createAudit.getCreatedName();
    }
    
    public String getCreatedCompany() {
        return createAudit.getCreatedCompany();
    }
    
    public String getCreatedUnit() {
        return createAudit.getCreatedUnit();
    }
    
    // 修改者相關
    public String getModifiedName() {
        return updateAudit.getModifiedName();
    }
    
    public String getModifiedCompany() {
        return updateAudit.getModifiedCompany();
    }
    
    public String getModifiedUnit() {
        return updateAudit.getModifiedUnit();
    }
    
    public String getDefaultLanguage() {
        return createAudit.getDefaultLanguage();
    }
    
    public void setDefaultLanguage(String defaultLanguage) {
        createAudit.setDefaultLanguage(defaultLanguage);
    }
} 