package com.example.fitnessspring.repositories;

import com.example.fitnessspring.models.entities.FitnessProgramEntity;
import com.example.fitnessspring.models.entities.ParticipationEntity;
import com.example.fitnessspring.models.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ParticipationRepository extends JpaRepository<ParticipationEntity, Integer> {
    boolean existsByUserAndFitnessprogram(UserEntity user, FitnessProgramEntity program);
    List<ParticipationEntity> getParticipationEntitiesByUserId(Integer id);
    List<ParticipationEntity> findByUserIdAndFitnessprogramEndDateAfter(Integer userId, LocalDate date);
    List<ParticipationEntity> findByUserIdAndFitnessprogramEndDateBefore(Integer userId, LocalDate date);
}
