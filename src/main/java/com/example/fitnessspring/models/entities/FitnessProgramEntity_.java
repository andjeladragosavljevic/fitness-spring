package com.example.fitnessspring.models.entities;

import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

import java.math.BigDecimal;
import java.time.LocalDate;

@StaticMetamodel(FitnessProgramEntity.class)
public class FitnessProgramEntity_ {
    public static volatile SingularAttribute<FitnessProgramEntity, String> name;
    public static volatile SingularAttribute<FitnessProgramEntity, String> description;
    public static volatile SingularAttribute<FitnessProgramEntity, Integer> id;
    public static volatile SingularAttribute<FitnessProgramEntity, LocalDate> startDate;
    public static volatile SingularAttribute<FitnessProgramEntity, LocalDate> endDate;
    public static volatile SingularAttribute<FitnessProgramEntity, UserEntity> user;
    public static volatile SingularAttribute<FitnessProgramEntity, BigDecimal> price;


}
