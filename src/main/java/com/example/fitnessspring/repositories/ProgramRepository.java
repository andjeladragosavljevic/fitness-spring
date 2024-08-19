package com.example.fitnessspring.repositories;

import com.example.fitnessspring.models.entities.FitnessProgramEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.LocalDate;

@Repository
public interface ProgramRepository extends JpaRepository<FitnessProgramEntity, Integer> {

    @Query("SELECT p FROM FitnessProgramEntity p " +
            "JOIN p.category c " +
            "JOIN FitnessprogramHasAttributeEntity fpa ON p.id = fpa.fitnessprogram.id " +
            "WHERE (:name IS NULL OR p.name LIKE %:name%) " +
            "AND (:description IS NULL OR p.description LIKE %:description%) " +
            "AND (:category IS NULL OR c.name = :category) " +
            "AND (:difficultyLevel IS NULL OR p.difficultyLevel = :difficultyLevel) " +
            "AND (:location IS NULL OR p.location LIKE %:location%) " +
            "AND (:instructor IS NULL OR p.user.firstName LIKE %:instructor%) " +
            "AND (:startDate IS NULL OR p.startDate >= :startDate) " +
            "AND (:endDate IS NULL OR p.endDate <= :endDate) " +
            "AND (:minPrice IS NULL OR p.price >= :minPrice) " +
            "AND (:maxPrice IS NULL OR p.price <= :maxPrice) " +
            "AND (:specificAttributeName IS NULL OR fpa.attribute.name = :specificAttributeName AND fpa.value = :specificAttributeValue)")
    Page<FitnessProgramEntity> filterPrograms(
            @Param("name") String name,
            @Param("description") String description,
            @Param("category") String category,
            @Param("difficultyLevel") String difficultyLevel,
            @Param("location") String location,
            @Param("instructor") String instructor,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("minPrice") BigDecimal minPrice,
            @Param("maxPrice") BigDecimal maxPrice,
            @Param("specificAttributeName") String specificAttributeName,
            @Param("specificAttributeValue") String specificAttributeValue,
            Pageable pageable);


    Page<FitnessProgramEntity> findFitnessProgramEntitiesByUserId(Pageable pageable, Integer userId);

    Page<FitnessProgramEntity> findFitnessProgramEntityByUserIdNot(Pageable pageable, Integer userId);

}
