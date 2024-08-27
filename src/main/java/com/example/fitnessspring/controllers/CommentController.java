package com.example.fitnessspring.controllers;

import com.example.fitnessspring.models.entities.Comment;
import com.example.fitnessspring.services.CommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
@CrossOrigin(origins = "http://localhost:4200")
public class CommentController {
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<Comment>> getComments(@PathVariable Integer id) {
        return ResponseEntity.ok(commentService.findByFitnessProgramId(id));
    }

    @GetMapping("/test/{id}")
    public ResponseEntity<Comment> getComment(@PathVariable Integer id) {
        return ResponseEntity.ok(commentService.findById(id));
    }

    @PostMapping
    public ResponseEntity<Comment> createComment(@RequestBody Comment comment) {
        return ResponseEntity.ok(commentService.save(comment));
    }

    @DeleteMapping("/{id}")
    public void deleteComment(@PathVariable Integer id){
        commentService.deleteById(id);
    }
}
