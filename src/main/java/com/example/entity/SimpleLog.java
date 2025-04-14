package com.example.entity;

import com.example.audit.CreateAuditInfo;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 * 簡單日誌實體 - 只使用創建審計
 * 通過組合模式嵌入創建審計資訊
 */
@Entity
@Table(name = "simple_log")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class SimpleLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "event_type", nullable = false)
    private String eventType;
    
    @Column(name = "message", nullable = false)
    private String message;
    
    @Column(name = "event_time")
    private LocalDateTime eventTime;
    
    // 只嵌入創建審計資訊，不包含修改審計
    @Embedded
    private CreateAuditInfo createAudit = new CreateAuditInfo();
    
    // 便捷方法已刪除，直接使用:
    // simpleLog.getCreateAudit().getCreatedName()
} 