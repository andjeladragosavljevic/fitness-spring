package com.example.fitnessspring.services.impl;

import com.example.fitnessspring.models.entities.User;
import com.example.fitnessspring.models.entities.UserEntity;
import com.example.fitnessspring.repositories.UserRepository;
import com.example.fitnessspring.services.LogService;
import com.example.fitnessspring.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final ModelMapper modelMapper;
    private final UserRepository userRepository;

   private final LogService logService;

    public UserServiceImpl(ModelMapper modelMapper, UserRepository userRepository, LogService logService) {
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;

        this.logService = logService;
    }

    @Override
    public User findByUsername(String username) {
        logService.log("INFO", "User found with username: " + username);
        return modelMapper.map(userRepository.findByUsername(username), User.class);
    }
    public User findById(Integer id) {
        if(userRepository.findById(id).isPresent()) {
            logService.log( "INFO", "User found with id: " + id);

            return modelMapper.map(userRepository.findById(id).get(), User.class);
        }
        logService.log("ERROR", "Error finding user with id: " + id);
        return null;
    }

    @Override
    public User save(User user) {
        UserEntity entity = modelMapper.map(user, UserEntity.class);

        entity = userRepository.saveAndFlush(entity);
        logService.log("INFO", "User saved with id: " + entity.getId());
        return findById(entity.getId());
    }


    public User activateUser(String activationCode) {
        UserEntity entity = userRepository.findByActivationCode(activationCode);
        entity.setActive(true);
        entity.setActivationCode(null);
        userRepository.saveAndFlush(entity);
        logService.log("INFO", "User activated with id: " + entity.getId());
        return findById(entity.getId());

    }


    public boolean existsByUsername(String username) {
        boolean exists = userRepository.existsByUsername(username);
        logService.log("INFO", "Checked if user exists by username: " + username + ". Result: " + exists);
        return exists;
    }

    public boolean existsByEmail(String email) {
        boolean exists = userRepository.existsByEmail(email);
        logService.log( "INFO", "Checked if user exists by email: " + email + ". Result: " + exists);
        return exists;
    }

    public User findByActivationCode(String activationCode) {
        UserEntity userEntity = userRepository.findByActivationCode(activationCode);
        logService.log("INFO", "User found with activation code: " + activationCode);

        return modelMapper.map(userEntity , User.class);
    }

    public List<User> getAvailableUsersForCommunication(Integer currentUserId) {
        logService.log("INFO", "Fetched available users for communication excluding current user with id: " + currentUserId);
        return userRepository.findAllByIdNot(currentUserId).stream().map(u -> modelMapper.map(u, User.class)).collect(Collectors.toList());
    }
}
