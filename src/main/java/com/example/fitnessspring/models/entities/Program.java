package com.example.fitnessspring.models.entities;

import lombok.*;

import java.math.BigDecimal;
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
    private BigDecimal duration;
    private String location;
    private String contact;
    private Category category;
    private Integer userId;
    private User instructor;
    private List<String> images;
    private List<SpecificAttribute> specificAttributes = new ArrayList<>();
}
