package com.example.fitnessspring.services;


import com.example.fitnessspring.models.entities.FilterDTO;
import com.example.fitnessspring.models.entities.FitnessProgramEntity;
import com.example.fitnessspring.models.entities.Program;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.LocalDate;


public interface ProgramService {
    public Page<Program> findAll(Pageable pageable);
    public Program findById(Integer id);
    public Program save(Program program);
    public Page<Program> findProgramsByUserId(Pageable pageable, Integer userId);
    public Page<Program> findProgramsByUserIdNot(Pageable pageable, Integer userId);
    public Program updateProgram(Program program);
    public void deleteProgram(Integer id);
    public Page<Program> filterPrograms(
            String name,
            String description,
            String category,
            String difficultyLevel,
            String location,
            String instructor,
            LocalDate startDate,
            LocalDate endDate,
            BigDecimal minPrice,
            BigDecimal maxPrice,
            String specificAttributeName,
            String specificAttributeValue,
            Pageable pageable);
}
