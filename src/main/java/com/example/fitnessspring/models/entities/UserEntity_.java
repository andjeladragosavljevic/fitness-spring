package com.example.fitnessspring.models.entities;

import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(UserEntity.class)
public class UserEntity_ {
    public static volatile SingularAttribute<UserEntity, Integer> id;
    public static volatile SingularAttribute<UserEntity, String> username;
    public static volatile SingularAttribute<UserEntity, String> email;
    //
}
