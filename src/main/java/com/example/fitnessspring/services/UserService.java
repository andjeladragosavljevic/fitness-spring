package com.example.fitnessspring.services;

import com.example.fitnessspring.models.entities.User;
import com.example.fitnessspring.models.entities.UserEntity;

import java.util.List;

public interface UserService {
    public User findByUsername(String username);
    public User save(User user);
    public User activateUser(String activationCode);
    public List<User> getAvailableUsersForCommunication(Integer currentUserId);
}
