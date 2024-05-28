package com.example.fitnessspring.repositories;

import com.example.fitnessspring.models.entities.CommentEntity;
import com.example.fitnessspring.models.entities.ImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends JpaRepository<ImageEntity, Integer> {

}
