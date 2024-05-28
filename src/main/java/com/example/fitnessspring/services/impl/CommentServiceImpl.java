package com.example.fitnessspring.services.impl;


import com.example.fitnessspring.models.entities.*;
import com.example.fitnessspring.repositories.CommentRepository;
import com.example.fitnessspring.services.CommentService;
import com.example.fitnessspring.util.CustomConverters;
import jakarta.websocket.server.PathParam;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final ModelMapper modelMapper;
    public CommentServiceImpl(CommentRepository commentsRepository, ModelMapper modelMapper) {
        this.commentRepository = commentsRepository;
        this.modelMapper = modelMapper;
        //this.modelMapper = CustomConverters.configureModelMapper();

    }

    @Override
    public List<Comment> findByFitnessProgramId(@PathParam("id") Integer id) {
        return commentRepository.findByFitnessprogramId(id).stream().map(a -> modelMapper.map(a, Comment.class)).collect(Collectors.toList());
    }

    public Comment findById(Integer id) {
        CommentEntity commentEntity = commentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Comment not found"));
        Comment comment = new Comment();
        comment.setId(commentEntity.getId());
        comment.setContent(commentEntity.getContent());
        comment.setCreatedAt(commentEntity.getCreatedAt());
        comment.setUserId(commentEntity.getUser().getId());
        comment.setUser(mapUserEntityToUser(commentEntity.getUser()));
        comment.setFitnessProgramId(commentEntity.getFitnessprogram().getId());
        return comment;
    }

    private User mapUserEntityToUser(UserEntity userEntity) {
        User user = new User();
        user.setId(userEntity.getId());
        user.setFirstName(userEntity.getFirstName());
        user.setLastName(userEntity.getLastName());
        return user;
    }
    @Override
    public Comment save(Comment comment) {
        CommentEntity entity = modelMapper.map(comment, CommentEntity.class);

        UserEntity user = new UserEntity();
        user.setId(comment.getUserId());
        entity.setUser(user);

        FitnessProgramEntity fitnessProgram = new FitnessProgramEntity();
        fitnessProgram.setId(comment.getFitnessProgramId());
        entity.setFitnessprogram(fitnessProgram);

        entity = commentRepository.saveAndFlush(entity);
        return findById(entity.getId());
    }
}
