package com.example.fitnessspring.repositories;

import com.example.fitnessspring.models.entities.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Integer> {
    public List<CommentEntity> findByFitnessprogramId(Integer fitnessprogramId);
}
