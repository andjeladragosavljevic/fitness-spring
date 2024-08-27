package com.example.fitnessspring.models.entities;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class Program {
    private Integer id;
    private String name;
    private String description;
    private BigDecimal price;
    private String difficultyLevel;
    private LocalDate startDate;
    private LocalDate endDate;
    private String location;
    private String contact;
    private Category category;
    private Integer userId;
    private User instructor;
    List<String> images;
    private List<SpecificAttribute> specificAttributes = new ArrayList<>();
    private String youtubeLink;

    public Program(Integer id, String name, String description, User instructor, Integer userId, Category category, String contact, String location, LocalDate endDate, LocalDate startDate, String difficultyLevel, BigDecimal price, String youtubeLink) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.instructor = instructor;
        this.userId = userId;
        this.category = category;
        this.contact = contact;
        this.location = location;
        this.endDate = endDate;
        this.startDate = startDate;
        this.difficultyLevel = difficultyLevel;
        this.price = price;
        this.youtubeLink = youtubeLink;

    }
}
