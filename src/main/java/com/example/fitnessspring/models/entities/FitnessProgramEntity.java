package com.example.fitnessspring.models.entities;

import com.example.fitnessspring.enums.DifficultyLevelEnum;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Data
@Entity
@Table(name = "fitnessprogram")
public class FitnessProgramEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;
    @Basic
    @Column(name = "name", nullable = false, length = 50)
    private String name;
    @Basic
    @Column(name = "description", nullable = true, length = 255)
    private String description;
    @Basic
    @Column(name = "price", nullable = false, precision = 2)
    private BigDecimal price;
    @Enumerated(EnumType.STRING)
    @Basic
    @Column(name = "difficulty_level", nullable = false)
    private DifficultyLevelEnum difficultyLevel;
    @Basic
    @Column(name="start_date")
    private LocalDate startDate;
    @Basic
    @Column(name="end_date")
    private LocalDate endDate;
    @Basic
    @Column(name = "location", nullable = false, length = 255)
    private String location;
    @Column(name = "contact", nullable = false, length = 255)
    private String contact;
    @OneToMany(mappedBy = "fitnessprogram")
    private List<CommentEntity> comments;
    @Basic
    @Column(name = "youtube_link")
    private String youtubeLink;
    @Basic
    @Column(name = "created_at", nullable = true)
    private LocalDateTime createdAt;
    @ManyToOne
    @JoinColumn(name = "Category_id", referencedColumnName = "id", nullable = false)
    private CategoryEntity category;
    @ManyToOne
    @JoinColumn(name = "User_id", referencedColumnName = "id", nullable = false)
    private UserEntity user;
    @OneToMany(mappedBy = "fitnessprogram", cascade = CascadeType.ALL)
    private List<FitnessprogramHasAttributeEntity> attributes;
    @OneToMany(mappedBy = "fitnessprogram", fetch =  FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ImageEntity> images;
    @OneToMany(mappedBy = "fitnessprogram")
    private List<ParticipationEntity> participations;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

}
