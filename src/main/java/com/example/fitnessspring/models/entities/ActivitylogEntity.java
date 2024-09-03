package com.example.fitnessspring.models.entities;

import com.example.fitnessspring.enums.DifficultyLevelEnum;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Objects;

@Data
@Entity
@Table(name = "activitylog")
public class ActivitylogEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;
    @Basic
    @Column(name = "created_at", nullable = true)
    private Timestamp createdAt;
    @Basic
    @Column(name = "exercise_type", nullable = true, length = 255)
    private String exerciseType;
    @Basic
    @Column(name = "duration", nullable = true, precision = 0)
    private Integer duration;
    @Enumerated(EnumType.STRING)
    @Basic
    @Column(name = "difficulty_level", nullable = true)
    private DifficultyLevelEnum difficultyLevel;
    @Basic
    @Column(name = "result", nullable = true, precision = 0)
    private Integer result;
    @ManyToOne
    @JoinColumn(name = "User_id", referencedColumnName = "id", nullable = false)
    private UserEntity user;

    @PrePersist
    protected void onCreated(){
        createdAt = Timestamp.valueOf(LocalDateTime.now());
    }

}
