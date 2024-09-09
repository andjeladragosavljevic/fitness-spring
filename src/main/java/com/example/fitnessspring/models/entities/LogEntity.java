package com.example.fitnessspring.models.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "logs")
public class LogEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "timestamp")
    private LocalDateTime timestamp;

    @Column(name = "log_level")
    private String logLevel;

    @Column(name = "message", columnDefinition = "TEXT")
    private String message;
}
