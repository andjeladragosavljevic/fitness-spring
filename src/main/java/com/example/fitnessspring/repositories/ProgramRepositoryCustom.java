package com.example.fitnessspring.repositories;

import com.example.fitnessspring.models.entities.Program;
import com.example.fitnessspring.models.entities.ProgramFilterDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProgramRepositoryCustom {
    List<Program> findOthersFilteredPrograms(ProgramFilterDTO filterDTO, Pageable pageable, Integer userId);
}
