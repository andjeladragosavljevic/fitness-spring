package com.example.fitnessspring.models.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@Entity
@Table(name = "category")
public class CategoryEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;
    @Basic
    @Column(name = "name", nullable = false, length = 50)
    private String name;
    @OneToMany(mappedBy = "category")
    private List<AttributeEntity> attributes;
    @OneToMany(mappedBy = "category")
    private List<FitnessProgramEntity> fitnessprograms;



}
