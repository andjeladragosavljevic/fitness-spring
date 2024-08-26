package com.example.fitnessspring.repositories;

import com.example.fitnessspring.models.entities.Program;
import com.example.fitnessspring.models.entities.ProgramFilterDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface ProgramRepositoryCustom {
    Page<Program> findFilteredPrograms(ProgramFilterDTO filterDTO, Pageable pageable, Integer userId, boolean isOwnPrograms);


}
