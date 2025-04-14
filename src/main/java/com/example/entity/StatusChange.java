package com.example.entity;

import com.example.audit.UpdateAuditInfo;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 * 狀態變更紀錄 - 這個實體僅使用更新審計欄位
 * 演示只需要更新審計欄位的場景
 */
@Entity
@Table(name = "status_change")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class StatusChange {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "entity_type", nullable = false)
    private String entityType;
    
    @Column(name = "entity_id", nullable = false)
    private Long entityId;
    
    @Column(name = "previous_status")
    private String previousStatus;
    
    @Column(name = "current_status", nullable = false)
    private String currentStatus;
    
    @Column(name = "change_time")
    private LocalDateTime changeTime;
    
    @Column(name = "remark")
    private String remark;
    
    // 只嵌入更新審計資訊，不使用創建審計
    @Embedded
    private UpdateAuditInfo updateAudit = new UpdateAuditInfo();
    
    // 提供便捷方法，使用和繼承相同的方式
    public String getModifiedName() {
        return updateAudit.getModifiedName();
    }
    
    public LocalDateTime getModifiedTime() {
        return updateAudit.getModifiedTime();
    }
    
    public String getModifiedCompany() {
        return updateAudit.getModifiedCompany();
    }
    
    public String getModifiedUnit() {
        return updateAudit.getModifiedUnit();
    }
} 