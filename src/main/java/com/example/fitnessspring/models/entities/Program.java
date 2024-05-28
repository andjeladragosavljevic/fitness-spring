package com.example.fitnessspring.models.entities;

import com.example.fitnessspring.enums.DifficultyLevelEnum;
import lombok.*;

import java.math.BigDecimal;
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
    private Integer categoryId;
    private Integer userId;
    private List<String> images;


}
