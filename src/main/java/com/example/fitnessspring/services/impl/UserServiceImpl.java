package com.example.fitnessspring.services.impl;

import com.example.fitnessspring.models.entities.User;
import com.example.fitnessspring.models.entities.UserEntity;
import com.example.fitnessspring.repositories.UserRepository;
import com.example.fitnessspring.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final ModelMapper modelMapper;
    private final UserRepository userRepository;

    public UserServiceImpl(ModelMapper modelMapper, UserRepository userRepository) {
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
    }

    @Override
    public User findByUsername(String username) {
        return modelMapper.map(userRepository.findByUsername(username), User.class);
    }
    public User findById(Integer id) {
        return modelMapper.map(userRepository.findById(id), User.class);
    }

    @Override
    public User save(User user) {
        UserEntity entity = modelMapper.map(user, UserEntity.class);
        entity = userRepository.saveAndFlush(entity);
        return findById(entity.getId());
    }
}
