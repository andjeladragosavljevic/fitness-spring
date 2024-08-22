package com.example.fitnessspring.models.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProgramFilterDTO {
    private String name;
    private String description;
    private Integer categoryId;
    private String difficultyLevel;
    private String location;
    private String instructor;
    private LocalDate startDate;
    private LocalDate endDate;
    private BigDecimal minPrice;
    private BigDecimal maxPrice;
    private String specificAttributeName;
    private String specificAttributeValue;

    public ProgramFilterDTO(Map<String, String> params) {
        this.name = params.get("name");
        this.description = params.get("description");
        this.difficultyLevel = params.get("difficultyLevel");
        this.location = params.get("location");
        this.instructor = params.get("instructor");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        if (params.get("category") != null) {
            this.categoryId =Integer.parseInt(params.get("category"));
        }

        if (params.get("startDate") != null) {
            this.startDate = LocalDate.parse(params.get("startDate"), formatter);

        }

        if (params.get("endDate") != null) {
            this.endDate = LocalDate.parse(params.get("endDate"), formatter);
        }


        if (params.get("minPrice") != null) {
            this.minPrice = new BigDecimal(params.get("minPrice"));
        }
        if (params.get("maxPrice") != null) {
            this.maxPrice = new BigDecimal(params.get("maxPrice"));
        }

        this.specificAttributeName = params.get("specificAttributeName");
        this.specificAttributeValue = params.get("specificAttributeValue");
    }
}
