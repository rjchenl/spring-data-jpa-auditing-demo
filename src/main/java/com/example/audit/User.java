package com.example.audit;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用戶實體類，代表資料庫中的一個用戶記錄
 */
@Entity
@Table(name = "pf_user")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    private Long id;
    
    @Column(name = "name")
    private String name;
    
    @Column(name = "company_id")
    private String company;
    
    @Column(name = "status_id")
    private String unit;
} 