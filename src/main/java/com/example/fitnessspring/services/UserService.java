package com.example.fitnessspring.services;

import com.example.fitnessspring.models.entities.User;

public interface UserService {
    public User findByUsername(String username);
    public User save(User user);
}
