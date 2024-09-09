package com.example.fitnessspring.services.impl;

import com.example.fitnessspring.enums.DifficultyLevelEnum;
import com.example.fitnessspring.models.entities.*;
import com.example.fitnessspring.repositories.AttributeRepository;
import com.example.fitnessspring.repositories.ImageRepository;
import com.example.fitnessspring.repositories.ProgramRepository;
import com.example.fitnessspring.services.LogService;
import com.example.fitnessspring.services.ProgramService;
import com.example.fitnessspring.util.CustomConverters;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;


@Service
public class ProgramServiceImpl implements ProgramService {

    final
    ImageRepository imageRepository;
    final
    AttributeRepository attributeRepository;

    private final ModelMapper modelMapper;
    private final ProgramRepository programRepository;

    private final LogService logService;

    public ProgramServiceImpl(ModelMapper modelMapper, ProgramRepository programRepository, AttributeRepository attributeRepository, ImageRepository imageRepository, LogService logService) {
        this.logService = logService;
        this.modelMapper = CustomConverters.configureModelMapper();
        this.programRepository = programRepository;
        this.attributeRepository = attributeRepository;
        this.imageRepository = imageRepository;
    }


    public Page<Program> findAll(Pageable pageable) {
        logService.log("INFO", "Fetched all programs");

        Page<FitnessProgramEntity> programsPage = programRepository.findAll(pageable);
        return programsPage.map(a -> modelMapper.map(a, Program.class));
    }


    public Program findById(Integer id) {
        logService.log("INFO", "Fetched activities with id: " + id);

        FitnessProgramEntity programEntity = programRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Program not found"));


        Program program = new Program();
        program.setId(programEntity.getId());
        program.setName(programEntity.getName());
        program.setDescription(programEntity.getDescription());
        program.setPrice(programEntity.getPrice());
        program.setDifficultyLevel(programEntity.getDifficultyLevel().name());
        program.setStartDate(programEntity.getStartDate());
        program.setEndDate(programEntity.getEndDate());
        program.setLocation(programEntity.getLocation());

        UserEntity userEntity = programEntity.getUser();
        if (userEntity != null) {
            User user = convertToUser(userEntity);
            program.setInstructor(user);
        }

        CategoryEntity categoryEntity = programEntity.getCategory();
        Category category = convertToCategory(categoryEntity);
        program.setCategory(category);


        List<String> imageUrls = new ArrayList<>();
        for (ImageEntity imageEntity : programEntity.getImages()) {
            imageUrls.add(imageEntity.getUrl());

        }

        List<SpecificAttribute> specificAttributes = new ArrayList<>();
        for (FitnessprogramHasAttributeEntity assoc : programEntity.getAttributes()) {
            SpecificAttribute attribute = new SpecificAttribute();
            attribute.setName(assoc.getAttribute().getName());
            attribute.setValue(assoc.getValue());
            specificAttributes.add(attribute);
        }
        program.setSpecificAttributes(specificAttributes);

        program.setImages(imageUrls);

        return program;
    }

    private User convertToUser(UserEntity userEntity) {
        User user = new User();
        user.setId(userEntity.getId());
        user.setFirstName(userEntity.getFirstName());
        user.setLastName(userEntity.getLastName());
        user.setEmail(userEntity.getEmail());
        return user;
    }

    private Category convertToCategory(CategoryEntity categoryEntity) {
        Category category = new Category();
        category.setId(categoryEntity.getId());
        category.setName(categoryEntity.getName());

        return category;
    }

    public Program save(Program program) {
        FitnessProgramEntity entity = new FitnessProgramEntity();
        entity.setId(program.getId());
        entity.setName(program.getName());
        entity.setDescription(program.getDescription());
        entity.setPrice(program.getPrice());
        entity.setDifficultyLevel(DifficultyLevelEnum.valueOf(program.getDifficultyLevel()));
        entity.setStartDate(program.getStartDate());
        entity.setEndDate(program.getEndDate());
        entity.setLocation(program.getLocation());
        entity.setContact(program.getContact());
        entity.setYoutubeLink(program.getYoutubeLink());


        CategoryEntity category = new CategoryEntity();
        category.setId(program.getCategoryId());
        entity.setCategory(category);

        UserEntity user = new UserEntity();
        user.setId(program.getUserId());
        entity.setUser(user);


        List<ImageEntity> images = updateImages(program, entity);
        entity.setImages(images);




        List<FitnessprogramHasAttributeEntity> specificAttributes = updateSpecificAttributes(program, entity);
        entity.setAttributes(specificAttributes);


        entity = programRepository.saveAndFlush(entity);

        logService.log("INFO", "Program added: " + entity.getId());

        return findById(entity.getId());

    }




    public Page<Program> findProgramsByUserId(Pageable pageable, Integer userId) {
        Page<FitnessProgramEntity> programsPage = programRepository.findFitnessProgramEntitiesByUserId(pageable, userId);
        return programsPage.map(a -> modelMapper.map(a, Program.class)); 

    }

    public Page<Program> findProgramsByUserIdNot(Pageable pageable, Integer userId) {
        logService.log("INFO", "Fetched programs for user: " + userId);

        Page<FitnessProgramEntity> programsPage = programRepository.findFitnessProgramEntityByUserIdNot(pageable, userId);
        return programsPage.map(a -> modelMapper.map(a, Program.class));
    }

    @Override
    public Program updateProgram(Integer id, Program program) {
        FitnessProgramEntity existingProgram = programRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Program not found"));

        existingProgram.setName(program.getName());
        existingProgram.setDescription(program.getDescription());
        existingProgram.setPrice(program.getPrice());
        existingProgram.setDifficultyLevel(DifficultyLevelEnum.valueOf(program.getDifficultyLevel()));
        existingProgram.setStartDate(program.getStartDate());
        existingProgram.setEndDate(program.getEndDate());
        existingProgram.setLocation(program.getLocation());
        existingProgram.setContact(program.getContact());
        existingProgram.setYoutubeLink(program.getYoutubeLink());


        List<FitnessprogramHasAttributeEntity> specificAttributes = updateSpecificAttributes(program, existingProgram);
        existingProgram.setAttributes(specificAttributes);

        List<String> removedImages = program.getRemovedImages();

        if (removedImages != null && !removedImages.isEmpty()) {
            List<ImageEntity> imagesToRemove = existingProgram.getImages().stream()
                    .filter(image -> removedImages.contains(image.getUrl()))
                    .toList();

            imagesToRemove.forEach(image -> {
                existingProgram.getImages().remove(image);
                imageRepository.delete(image);
            });
        }

        List<String> newImages = program.getImages();
        List<ImageEntity> updatedImages = existingProgram.getImages();

        for (String imageUrl : newImages) {
            boolean imageExists = existingProgram.getImages().stream()
                    .anyMatch(image -> image.getUrl().equals(imageUrl));

            if (!imageExists) {
                ImageEntity newImage = new ImageEntity();
                newImage.setUrl(imageUrl);
                newImage.setFitnessprogram(existingProgram);
                updatedImages.add(newImage);
            }
        }

        existingProgram.setImages(updatedImages);


        FitnessProgramEntity savedProgram = programRepository.saveAndFlush(existingProgram);

        logService.log("INFO", "Program updated: " + id);

        return findById(savedProgram.getId());
    }

    private List<FitnessprogramHasAttributeEntity> updateSpecificAttributes(Program program, FitnessProgramEntity entity) {
        List<String> attributeNames = program.getSpecificAttributes().stream()
                .map(SpecificAttribute::getName)
                .collect(Collectors.toList());

        List<AttributeEntity> attributeEntities = attributeRepository.findByNameIn(attributeNames);

        Map<String, AttributeEntity> attributeEntityMap = attributeEntities.stream()
                .collect(Collectors.toMap(AttributeEntity::getName, Function.identity()));

        List<FitnessprogramHasAttributeEntity> specificAttributes = new ArrayList<>();
        for (SpecificAttribute attribute : program.getSpecificAttributes()) {
            AttributeEntity attributeEntity = attributeEntityMap.get(attribute.getName());
            if (attributeEntity == null) {
                throw new EntityNotFoundException("Attribute with name " + attribute.getName() + " not found");
            }

            FitnessprogramHasAttributeEntity fitnessprogramHasAttributeEntity = new FitnessprogramHasAttributeEntity();
            fitnessprogramHasAttributeEntity.setAttribute(attributeEntity);
            fitnessprogramHasAttributeEntity.setValue(attribute.getValue());
            fitnessprogramHasAttributeEntity.setFitnessprogram(entity);
            specificAttributes.add(fitnessprogramHasAttributeEntity);
        }
        return specificAttributes;
    }


    private List<ImageEntity> updateImages(Program program, FitnessProgramEntity entity) {
        List<ImageEntity> images = new ArrayList<>();
        for (String imageUrl : program.getImages()) {
            ImageEntity image = new ImageEntity();
            image.setUrl(imageUrl);
            image.setFitnessprogram(entity);
            images.add(image);
        }
        return images;
    }

    @Override
    public void deleteProgram(Integer id) {

        programRepository.deleteById(id);
        logService.log("WARN", "Program deleted: " + id);
    }


    @Override
    public Page<Program> filterPrograms(ProgramFilterDTO filterDTO, Pageable pageable, Integer userId, boolean isOwnPrograms) {
     return programRepository.findFilteredPrograms(filterDTO, pageable, userId, isOwnPrograms);
    }




}
