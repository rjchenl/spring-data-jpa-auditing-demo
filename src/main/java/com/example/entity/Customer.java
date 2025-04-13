package com.example.entity;

import com.example.audit.AuditMetadata;
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

    @Embedded
    private AuditMetadata auditMetadata = new AuditMetadata();
} 