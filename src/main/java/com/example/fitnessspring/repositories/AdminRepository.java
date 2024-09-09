package com.example.fitnessspring.repositories;

import com.example.fitnessspring.models.entities.AdminEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdminRepository extends JpaRepository<AdminEntity, Integer> {
    List<AdminEntity> findByRole(AdminEntity.Role role);
}
