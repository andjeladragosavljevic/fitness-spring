package com.example.fitnessspring.services.impl;

import com.example.fitnessspring.models.entities.Category;
import com.example.fitnessspring.models.entities.CategoryEntity;
import com.example.fitnessspring.repositories.CategoryRepository;
import com.example.fitnessspring.services.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final ModelMapper modelMapper;
    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(ModelMapper modelMapper, CategoryRepository categoryRepository) {
        this.modelMapper = modelMapper;
        this.categoryRepository = categoryRepository;
    }


    public List<Category> findAll() {
        return categoryRepository.findAll().stream().map(a -> modelMapper.map(a, Category.class)).collect(Collectors.toList());
    }

    public Category findById(Integer id) {
        return modelMapper.map(categoryRepository.findById(id), Category.class);
    }

   public Category save(Category category) {
       CategoryEntity entity = modelMapper.map(category, CategoryEntity.class);
       entity = categoryRepository.saveAndFlush(entity);
       return findById(entity.getId());
   }

}
