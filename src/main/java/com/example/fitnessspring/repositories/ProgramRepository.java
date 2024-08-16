package com.example.fitnessspring.repositories;

import com.example.fitnessspring.models.entities.FitnessProgramEntity;
import com.example.fitnessspring.models.entities.Program;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;
@Repository
public interface ProgramRepository extends JpaRepository<FitnessProgramEntity, Integer> {

//    Page<FitnessProgramEntity> findAll(Pageable pageable);
    Page<FitnessProgramEntity> findFitnessProgramEntitiesByUserId(Pageable pageable, Integer userId);
    Page<FitnessProgramEntity> findFitnessProgramEntityByUserIdNot(Pageable pageable, Integer userId);

}
