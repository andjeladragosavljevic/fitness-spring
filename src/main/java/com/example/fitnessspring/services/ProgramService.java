package com.example.fitnessspring.services;


import com.example.fitnessspring.models.entities.Program;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;



public interface ProgramService {
    public Page<Program> findAll(Pageable pageable);
    public Program findById(Integer id);
    public Program save(Program program);

}
