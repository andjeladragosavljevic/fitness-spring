package com.example.fitnessspring.controllers;

import com.example.fitnessspring.models.entities.User;
import com.example.fitnessspring.models.entities.UserEntity;
import com.example.fitnessspring.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/available")
    public List<User> getAvailableUsersForCommunication(@RequestParam Integer currentUserId) {
        return userService.getAvailableUsersForCommunication(currentUserId);
    }
}
