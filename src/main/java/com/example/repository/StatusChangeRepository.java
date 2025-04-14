package com.example.repository;

import com.example.entity.StatusChange;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StatusChangeRepository extends JpaRepository<StatusChange, Long> {
    
    List<StatusChange> findByEntityTypeAndEntityId(String entityType, Long entityId);
    
    List<StatusChange> findByCurrentStatus(String currentStatus);
} 