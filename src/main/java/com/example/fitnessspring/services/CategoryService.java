package com.example.fitnessspring.services;


import com.example.fitnessspring.models.entities.Category;

import java.util.List;

public interface CategoryService {
    public List<Category> findAll();
    public Category save(Category category);
}
