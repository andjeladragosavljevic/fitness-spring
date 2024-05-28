package com.example.fitnessspring.repositories;

import com.example.fitnessspring.models.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    UserEntity findByUsername(String username);
    UserEntity findByActivationCode(String activationCode);
}
