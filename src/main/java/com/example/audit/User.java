package com.example.audit;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * 用戶引用類，用於審計欄位中嵌入用戶信息
 */
@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class User {
    @Column(name = "id")
    private Long id;
    
    private String name;

    private String username;
    private String displayName;
    private String company;
    private String unit;
    
    public User(Long id, String username) {
        this.id = id;
        this.username = username;
        this.displayName = username;
    }
    
    public User(String username, String displayName) {
        this.username = username;
        this.displayName = displayName;
    }
    
    public User(Long id, String username, String displayName) {
        this.id = id;
        this.username = username;
        this.displayName = displayName;
    }

    public User(Long id, String username, String displayName, String company, String unit) {
        this.id = id;
        this.username = username;
        this.displayName = displayName;
        this.company = company;
        this.unit = unit;
    }
    
    /**
     * 創建同時設置name為displayName的構造函數
     */
    public User(Long id, String username, String displayName, String company, String unit, boolean setNameAsDisplayName) {
        this.id = id;
        this.username = username;
        this.displayName = displayName;
        this.company = company;
        this.unit = unit;
        if (setNameAsDisplayName) {
            this.name = displayName;
        }
    }
} 