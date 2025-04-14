package com.example.repository;

import com.example.entity.SimpleLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SimpleLogRepository extends JpaRepository<SimpleLog, Long> {
} 