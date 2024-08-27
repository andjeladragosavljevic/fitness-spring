package com.example.fitnessspring.models.entities;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Objects;

@Data
@Getter
@Setter
@Entity
@Table(name = "comment")
public class CommentEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;
    @Basic
    @Column(name = "content", nullable = false, length = 255)
    private String content;
    @Basic
    @Column(name = "created_at", nullable = true)
    private Timestamp createdAt;
    @ManyToOne
    @JoinColumn(name = "User_id", referencedColumnName = "id", nullable = false)
    private UserEntity user;
    @ManyToOne
    @JoinColumn(name = "fitness_program_id", referencedColumnName = "id", nullable = false)
    private FitnessProgramEntity fitnessprogram;

    @PrePersist
    protected void onCreate() {
        createdAt = Timestamp.valueOf(LocalDateTime.now());
    }

}
