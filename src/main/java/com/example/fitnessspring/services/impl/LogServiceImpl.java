package com.example.fitnessspring.services.impl;

import com.example.fitnessspring.models.entities.LogEntity;
import com.example.fitnessspring.repositories.LogRepository;
import com.example.fitnessspring.services.LogService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class LogServiceImpl implements LogService {
    private final LogRepository logRepository;

    public LogServiceImpl(LogRepository logRepository) {
        this.logRepository = logRepository;
    }

    public void log(String level, String message) {
        LogEntity logEntry = new LogEntity();
        logEntry.setTimestamp(LocalDateTime.now());
        logEntry.setLogLevel(level);
        logEntry.setMessage(message);
        logRepository.save(logEntry);
    }
}
