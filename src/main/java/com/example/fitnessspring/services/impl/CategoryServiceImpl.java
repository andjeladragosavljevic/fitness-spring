package com.example.fitnessspring.services.impl;

import com.example.fitnessspring.models.entities.Category;
import com.example.fitnessspring.models.entities.CategoryEntity;
import com.example.fitnessspring.repositories.CategoryRepository;
import com.example.fitnessspring.services.CategoryService;
import com.example.fitnessspring.services.LogService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final ModelMapper modelMapper;
    private final CategoryRepository categoryRepository;
    private final LogService logService;

    public CategoryServiceImpl(ModelMapper modelMapper, CategoryRepository categoryRepository, LogService logService) {
        this.modelMapper = modelMapper;
        this.categoryRepository = categoryRepository;
        this.logService = logService;
    }


    public List<Category> findAll() {
        logService.log("INFO", "Fetched all categories" );
        return categoryRepository.findAll().stream().map(a -> modelMapper.map(a, Category.class)).collect(Collectors.toList());
    }

    public Category findById(Integer id) {
        logService.log("INFO", "Fetched category with id: " + id);
        return modelMapper.map(categoryRepository.findById(id), Category.class);
    }

   public Category save(Category category) {
       CategoryEntity entity = modelMapper.map(category, CategoryEntity.class);
       entity = categoryRepository.saveAndFlush(entity);

       logService.log("INFO","Saved category with id: " + entity.getId());
       return findById(entity.getId());
   }

}
