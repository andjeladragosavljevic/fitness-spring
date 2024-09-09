package com.example.fitnessspring.repositories;

import com.example.fitnessspring.models.entities.MessagetoadvisorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageToAdvisorRepository extends JpaRepository<MessagetoadvisorEntity, Integer> {
}
