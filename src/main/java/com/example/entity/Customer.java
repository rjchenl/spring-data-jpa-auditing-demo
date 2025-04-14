package com.example.entity;

import com.example.audit.CreateAuditInfo;
import com.example.audit.UpdateAuditInfo;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * 客戶實體 - 使用完整審計（創建和修改）
 * 通過組合模式嵌入審計資訊
 */
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
    
    // 便捷方法已刪除，直接使用:
    // customer.getCreateAudit().getCreatedName()
    // customer.getUpdateAudit().getModifiedName()
} 