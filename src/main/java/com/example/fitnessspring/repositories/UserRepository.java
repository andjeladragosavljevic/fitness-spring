package com.example.fitnessspring.repositories;

import com.example.fitnessspring.models.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    UserEntity findByUsername(String username);


    List<UserEntity> findAllByIdNot(@Param("currentUserId") Integer currentUserId);

    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    UserEntity findByActivationCode(String activationCode);
}
