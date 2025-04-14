package com.example.controller;

import com.example.entity.SimpleLog;
import com.example.repository.SimpleLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/logs")
public class SimpleLogController {

    @Autowired
    private SimpleLogRepository simpleLogRepository;

    @GetMapping
    public List<SimpleLog> getAllLogs() {
        return simpleLogRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<SimpleLog> getLogById(@PathVariable Long id) {
        return simpleLogRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public SimpleLog createLog(@RequestBody SimpleLog log) {
        if (log.getEventTime() == null) {
            log.setEventTime(LocalDateTime.now());
        }
        return simpleLogRepository.save(log);
    }

    // 注意：這個實體無需修改審計欄位，只有創建審計欄位
    // 但業務欄位可以修改，如消息內容或事件類型
    @PutMapping("/{id}")
    public ResponseEntity<SimpleLog> updateLog(@PathVariable Long id, @RequestBody SimpleLog logDetails) {
        return simpleLogRepository.findById(id)
                .map(log -> {
                    log.setEventType(logDetails.getEventType());
                    log.setMessage(logDetails.getMessage());
                    log.setEventTime(logDetails.getEventTime());
                    return ResponseEntity.ok(simpleLogRepository.save(log));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteLog(@PathVariable Long id) {
        return simpleLogRepository.findById(id)
                .map(log -> {
                    simpleLogRepository.delete(log);
                    return ResponseEntity.ok().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
} 