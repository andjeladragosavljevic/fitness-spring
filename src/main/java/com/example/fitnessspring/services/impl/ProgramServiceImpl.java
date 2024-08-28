package com.example.fitnessspring.services.impl;

import com.example.fitnessspring.enums.DifficultyLevelEnum;
import com.example.fitnessspring.models.entities.*;
import com.example.fitnessspring.repositories.AttributeRepository;
import com.example.fitnessspring.repositories.ProgramRepository;
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
    AttributeRepository attributeRepository;

    private final ModelMapper modelMapper;
    private final ProgramRepository programRepository;

    public ProgramServiceImpl(ModelMapper modelMapper, ProgramRepository programRepository, AttributeRepository attributeRepository) {
        this.modelMapper = CustomConverters.configureModelMapper();
        this.programRepository = programRepository;
        this.attributeRepository = attributeRepository;
    }


    public Page<Program> findAll(Pageable pageable) {
        Page<FitnessProgramEntity> programsPage = programRepository.findAll(pageable);
        return programsPage.map(a -> modelMapper.map(a, Program.class)); //.collect(Collectors.toList());
    }


    public Program findById(Integer id) {
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


        List<ImageEntity> images = new ArrayList<>();
        for (String imageUrl : program.getImages()) {
            ImageEntity image = new ImageEntity();
            image.setUrl(imageUrl);
            image.setFitnessprogram(entity);
            images.add(image);
        }
        entity.setImages(images);

        Map<String, AttributeEntity> attributeEntityMap = attributeRepository.findByNameIn(
                        program.getSpecificAttributes().stream()
                                .map(SpecificAttribute::getName)
                                .collect(Collectors.toList())
                ).stream()
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
        entity.setAttributes(specificAttributes);


        entity = programRepository.saveAndFlush(entity);
        return findById(entity.getId());

    }


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
