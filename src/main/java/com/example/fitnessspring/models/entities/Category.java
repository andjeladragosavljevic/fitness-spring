package com.example.fitnessspring.models.entities;

import lombok.*;

@NoArgsConstructor
@Data
public class Category {
    private Integer id;
    private String name;

    public Category(Integer id, String name) {
        this.id = id;
        this.name = name;
    }
}
