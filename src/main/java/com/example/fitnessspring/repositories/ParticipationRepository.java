package com.example.fitnessspring.repositories;

import com.example.fitnessspring.models.entities.FitnessProgramEntity;
import com.example.fitnessspring.models.entities.Participation;
import com.example.fitnessspring.models.entities.ParticipationEntity;
import com.example.fitnessspring.models.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParticipationRepository extends JpaRepository<ParticipationEntity, Integer> {
    boolean existsByUserAndFitnessprogram(UserEntity user, FitnessProgramEntity program);
}
