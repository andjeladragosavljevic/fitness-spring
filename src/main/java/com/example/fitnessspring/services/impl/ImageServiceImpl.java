package com.example.fitnessspring.services.impl;

import com.example.fitnessspring.models.entities.Image;
import com.example.fitnessspring.models.entities.ImageEntity;
import com.example.fitnessspring.repositories.ImageRepository;
import com.example.fitnessspring.services.ImageService;
import com.example.fitnessspring.services.LogService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class ImageServiceImpl implements ImageService {
    private final ModelMapper modelMapper;
    private final ImageRepository imageRepository;
    private final LogService logService;

    public ImageServiceImpl(ModelMapper modelMapper, ImageRepository imageRepository, LogService logService) {
        this.modelMapper = modelMapper;
        this.imageRepository = imageRepository;
        this.logService = logService;
    }

    public Image findById(Integer id) {
        logService.log("INFO", "Fetched image with id: " + id);

        return modelMapper.map(imageRepository.findById(id), Image.class);
    }

    public Image save(Image image) {
        ImageEntity imageEntity = modelMapper.map(image, ImageEntity.class);
        imageEntity = imageRepository.saveAndFlush(imageEntity);

        logService.log("INFO", "Image added: " + imageEntity.getId());

        return findById(imageEntity.getId());

    }




}
