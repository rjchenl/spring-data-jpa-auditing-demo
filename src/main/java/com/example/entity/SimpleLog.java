package com.example.entity;

import com.example.audit.CreateAuditBase;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "simple_log")
@Getter
@Setter
public class SimpleLog extends CreateAuditBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "event_type", nullable = false)
    private String eventType;
    
    @Column(name = "message", nullable = false)
    private String message;
    
    @Column(name = "event_time")
    private LocalDateTime eventTime;
} 