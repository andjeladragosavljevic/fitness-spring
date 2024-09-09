package com.example.fitnessspring.services.impl;


import com.example.fitnessspring.models.entities.*;
import com.example.fitnessspring.repositories.CommentRepository;
import com.example.fitnessspring.repositories.ProgramRepository;
import com.example.fitnessspring.repositories.UserRepository;
import com.example.fitnessspring.services.CommentService;
import com.example.fitnessspring.services.LogService;
import jakarta.websocket.server.PathParam;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    final
    ProgramRepository programRepository;
    final
    UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final LogService logService;
    private final ModelMapper modelMapper;
    public CommentServiceImpl(CommentRepository commentsRepository, ModelMapper modelMapper, ProgramRepository programRepository, UserRepository userRepository, LogService logService) {
        this.commentRepository = commentsRepository;
        this.modelMapper = modelMapper;
        //this.modelMapper = CustomConverters.configureModelMapper();

        this.programRepository = programRepository;
        this.userRepository = userRepository;
        this.logService = logService;
    }

    @Override
    public List<Comment> findByFitnessProgramId(@PathParam("id") Integer id) {
        logService.log("INFO", "Fetched comments for user: " + id);
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

        logService.log("INFO", "Fetched comment with id: " + id);

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
        CommentEntity entity = new CommentEntity();

        UserEntity userEntity = userRepository.findById(comment.getUserId()).orElseThrow(() -> new RuntimeException("User not found"));
        FitnessProgramEntity programEntity = programRepository.findById(comment.getFitnessProgramId()).orElseThrow(() -> new RuntimeException("Program not found"));

        entity.setFitnessprogram(programEntity);
        entity.setCreatedAt(comment.getCreatedAt());
        entity.setUser(userEntity);

        entity.setContent(comment.getContent());


        entity = commentRepository.saveAndFlush(entity);

        logService.log("INFO", "Comment added: " + entity.getId());

        return modelMapper.map(entity, Comment.class);
    }

    @Override
    public void deleteById(Integer id){
        logService.log("WARN", "Comment deleted: " + id);

        commentRepository.deleteById(id);
    }
}
