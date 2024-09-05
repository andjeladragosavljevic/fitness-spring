package com.example.fitnessspring.repositories;

import com.example.fitnessspring.models.entities.MessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<MessageEntity, Integer> {

}
