package com.example.fitnessspring.controllers;

import com.example.fitnessspring.models.entities.User;
import com.example.fitnessspring.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ActivationController {

    private final UserService userService;

    public ActivationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/activate")
    public ResponseEntity<User> activateUser(@RequestParam String code) {
       return ResponseEntity.ok(userService.activateUser(code));

    }
}
