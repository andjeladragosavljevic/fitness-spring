package com.example.fitnessspring.services.impl;

import com.example.fitnessspring.enums.DifficultyLevelEnum;
import com.example.fitnessspring.models.entities.*;
import com.example.fitnessspring.repositories.ProgramRepository;
import com.example.fitnessspring.services.ProgramService;
import com.example.fitnessspring.util.CustomConverters;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class ProgramServiceImpl implements ProgramService {
    private final ModelMapper modelMapper;
    private final ProgramRepository programRepository;

    public ProgramServiceImpl(ModelMapper modelMapper, ProgramRepository programRepository) {
        this.modelMapper = CustomConverters.configureModelMapper();
        this.programRepository = programRepository;
    }


    public Page<Program> findAll(Pageable pageable) {
        Page<FitnessProgramEntity> programsPage = programRepository.findAll(pageable);
        return programsPage.map(a -> modelMapper.map(a, Program.class)); //.collect(Collectors.toList());
    }


    public Program findById(Integer id) {
        FitnessProgramEntity programEntity = programRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Program not found"));

        // Manual mapping from FitnessProgramEntity to Program
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

        // Map images manually
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
        // Manual mapping of images
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

        // Set category and user from their IDs
        CategoryEntity category = new CategoryEntity();
        category.setId(program.getCategory().getId());
        entity.setCategory(category);

        UserEntity user = new UserEntity();
        user.setId(program.getUserId());
        entity.setUser(user);

        // Map images
        List<ImageEntity> images = new ArrayList<>();
        for (String imageUrl : program.getImages()) {
            ImageEntity image = new ImageEntity();
            image.setUrl(imageUrl);
            image.setFitnessprogram(entity);
            images.add(image);
        }

        entity.setImages(images);

        // Save the entity
        entity = programRepository.saveAndFlush(entity);
        return findById(entity.getId());
//        FitnessProgramEntity entity = modelMapper.map(program, FitnessProgramEntity.class);
//
//
//        List<ImageEntity> images = new ArrayList<>();
//        for (String imageUrl : program.getImages()) {
//            ImageEntity image = new ImageEntity();
//            image.setUrl(imageUrl);
//            image.setFitnessprogram(entity);
//            images.add(image);
//        }
//
//        entity.setImages(images);
//        entity = programRepository.saveAndFlush(entity);
//        return findById(entity.getId());
    }

   // public void delete(Integer id) {}

    public Page<Program> findProgramsByUserId(Pageable pageable, Integer userId) {
        Page<FitnessProgramEntity> programsPage = programRepository.findFitnessProgramEntitiesByUserId(pageable, userId);
        return programsPage.map(a -> modelMapper.map(a, Program.class)); //.collect(Collectors.toList());

    }

    public Page<Program> findProgramsByUserIdNot(Pageable pageable, Integer userId) {
        Page<FitnessProgramEntity> programsPage = programRepository.findFitnessProgramEntityByUserIdNot(pageable, userId);
        return programsPage.map(a -> modelMapper.map(a, Program.class));
    }

    @Override
    public Program updateProgram(Program program) {
        return null;
    }

    @Override
    public void deleteProgram(Integer id) {
        programRepository.deleteById(id);
    }


    @Override
    public Page<Program> filterPrograms(ProgramFilterDTO filterDTO, Pageable pageable, Integer userId, boolean isOwnPrograms) {

     return programRepository.findFilteredPrograms(filterDTO, pageable, userId, isOwnPrograms);


    }




}
