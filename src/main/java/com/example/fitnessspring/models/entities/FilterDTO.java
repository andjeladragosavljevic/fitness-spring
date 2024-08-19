package com.example.fitnessspring.models.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class FilterDTO {
    private String name;
    private String description;
    private String category;
    private String difficultyLevel;
    private String location;
    private String instructor;
    private LocalDate startDate;
    private LocalDate endDate;
    private BigDecimal minPrice;
    private BigDecimal maxPrice;
    private String specificAttributeName;
    private String specificAttributeValue;
}
