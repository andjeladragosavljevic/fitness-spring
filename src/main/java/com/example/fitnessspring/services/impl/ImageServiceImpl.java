package com.example.fitnessspring.services.impl;

import com.example.fitnessspring.models.entities.Image;
import com.example.fitnessspring.models.entities.ImageEntity;
import com.example.fitnessspring.repositories.ImageRepository;
import com.example.fitnessspring.services.ImageService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class ImageServiceImpl implements ImageService {
    private final ModelMapper modelMapper;
    private final ImageRepository imageRepository;

    public ImageServiceImpl(ModelMapper modelMapper, ImageRepository imageRepository) {
        this.modelMapper = modelMapper;
        this.imageRepository = imageRepository;
    }

    public Image findById(Integer id) {
        return modelMapper.map(imageRepository.findById(id), Image.class);
    }

    public Image save(Image image) {
        ImageEntity imageEntity = modelMapper.map(image, ImageEntity.class);
        imageEntity = imageRepository.saveAndFlush(imageEntity);
        return findById(imageEntity.getId());

    }




}
