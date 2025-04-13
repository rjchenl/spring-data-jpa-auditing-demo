package com.example.audit;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.Instant;

@Embeddable
@Getter
@Setter
public class AuditMetadata {
    @CreatedBy
    @ManyToOne
    @JoinColumn(name = "created_by", updatable = false)
    private User createdBy;

    @CreatedDate
    @Column(name = "created_time", updatable = false)
    private Instant createdTime;

    @LastModifiedBy
    @ManyToOne
    @JoinColumn(name = "modified_by")
    private User modifiedBy;

    @LastModifiedDate
    @Column(name = "modified_time")
    private Instant modifiedTime;

    @Column(name = "created_company")
    private String createdCompany;

    @Column(name = "modified_company")
    private String modifiedCompany;

    @Column(name = "created_unit")
    private String createdUnit;

    @Column(name = "modified_unit")
    private String modifiedUnit;
} 