package com.example.entity;

import com.example.audit.UpdateAuditInfo;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 * 狀態變更紀錄 - 這個實體僅使用更新審計欄位
 * 通過組合模式嵌入更新審計資訊
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
    
    // 只嵌入更新審計資訊
    @Embedded
    private UpdateAuditInfo updateAudit = new UpdateAuditInfo();

} 