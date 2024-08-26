package com.example.fitnessspring.repositories;

import com.example.fitnessspring.models.entities.FitnessProgramEntity;
import com.example.fitnessspring.models.entities.Program;
import com.example.fitnessspring.models.entities.ProgramFilterDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Repository
public interface ProgramRepository extends JpaRepository<FitnessProgramEntity, Integer>, ProgramRepositoryCustom {


    Page<FitnessProgramEntity> findFitnessProgramEntitiesByUserId(Pageable pageable, Integer userId);

    Page<FitnessProgramEntity> findFitnessProgramEntityByUserIdNot(Pageable pageable, Integer userId);


}
