package com.example.fitnessspring.services;

import com.example.fitnessspring.models.entities.Image;

public interface ImageService {
    public Image findById(Integer id);
    public Image save(Image image);
}
