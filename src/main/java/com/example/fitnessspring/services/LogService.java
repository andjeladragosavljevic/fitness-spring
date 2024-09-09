package com.example.fitnessspring.services;

import org.springframework.stereotype.Service;

@Service
public interface LogService {
    public void log(String level, String message);
}
