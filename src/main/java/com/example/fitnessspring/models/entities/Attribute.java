package com.example.fitnessspring.models.entities;

import lombok.Data;


@Data
public class Attribute {
    private Integer id;
    private String name;
    private Category category;

}
