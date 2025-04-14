package com.example.audit;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

/**
 * 可嵌入的更新審計資訊類
 * 用於組合模式審計機制
 */
@Embeddable
@Getter
@Setter
public class ModifiedAuditInfo {
    
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
    private User modifier;

    // 標準審計欄位 - 修改時間
    @LastModifiedDate
    @Column(name = "modified_time", nullable = false, precision = 6)
    private LocalDateTime modifiedTime;


} 