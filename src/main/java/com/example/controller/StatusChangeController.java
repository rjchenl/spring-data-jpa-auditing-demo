package com.example.controller;

import com.example.entity.StatusChange;
import com.example.repository.StatusChangeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/status-changes")
public class StatusChangeController {

    @Autowired
    private StatusChangeRepository statusChangeRepository;

    @GetMapping
    public List<StatusChange> getAllStatusChanges() {
        return statusChangeRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<StatusChange> getStatusChangeById(@PathVariable Long id) {
        return statusChangeRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/entity/{entityType}/{entityId}")
    public List<StatusChange> getStatusChangesByEntity(
            @PathVariable String entityType,
            @PathVariable Long entityId) {
        return statusChangeRepository.findByEntityTypeAndEntityId(entityType, entityId);
    }

    @PostMapping
    public StatusChange createStatusChange(@RequestBody StatusChange statusChange) {
        if (statusChange.getChangeTime() == null) {
            statusChange.setChangeTime(LocalDateTime.now());
        }
        return statusChangeRepository.save(statusChange);
    }

    @PutMapping("/{id}")
    public ResponseEntity<StatusChange> updateStatusChange(
            @PathVariable Long id,
            @RequestBody StatusChange statusChangeDetails) {
        
        return statusChangeRepository.findById(id)
                .map(statusChange -> {
                    statusChange.setEntityType(statusChangeDetails.getEntityType());
                    statusChange.setEntityId(statusChangeDetails.getEntityId());
                    statusChange.setPreviousStatus(statusChangeDetails.getPreviousStatus());
                    statusChange.setCurrentStatus(statusChangeDetails.getCurrentStatus());
                    statusChange.setChangeTime(statusChangeDetails.getChangeTime());
                    statusChange.setRemark(statusChangeDetails.getRemark());
                    
                    return ResponseEntity.ok(statusChangeRepository.save(statusChange));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteStatusChange(@PathVariable Long id) {
        return statusChangeRepository.findById(id)
                .map(statusChange -> {
                    statusChangeRepository.delete(statusChange);
                    return ResponseEntity.ok().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
} 