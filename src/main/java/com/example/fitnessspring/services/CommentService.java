package com.example.fitnessspring.services;

import com.example.fitnessspring.models.entities.Comment;

import java.util.List;


public interface CommentService {
    public List<Comment> findByFitnessProgramId(Integer fitnessProgramId);
    public Comment save(Comment comment);
    public Comment findById(Integer id);
    public void deleteById(Integer id);

}
