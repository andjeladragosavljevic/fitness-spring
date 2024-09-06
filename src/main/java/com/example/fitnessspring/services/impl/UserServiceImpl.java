package com.example.fitnessspring.services.impl;

import com.example.fitnessspring.models.entities.User;
import com.example.fitnessspring.models.entities.UserEntity;
import com.example.fitnessspring.repositories.UserRepository;
import com.example.fitnessspring.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final ModelMapper modelMapper;
    private final UserRepository userRepository;

    private final JavaMailSender mailSender;

    public UserServiceImpl(ModelMapper modelMapper, UserRepository userRepository, JavaMailSender mailSender) {
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
        this.mailSender = mailSender;
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
        entity.setActivationCode(UUID.randomUUID().toString());
        entity = userRepository.saveAndFlush(entity);
        sendActivationEmail(user.getEmail(), entity.getActivationCode());
        return findById(entity.getId());
    }
    public void sendActivationEmail(String email, String activationCode) {
        String activationLink = "http://localhost:8080/api/activate?code=" + activationCode;
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("andjadragos@icloud.com");
        message.setTo(email);
        message.setSubject("Account Activation");
        message.setText("Click the link to activate your account: " + activationLink);
        mailSender.send(message);
    }

    public User activateUser(String activationCode) {
        UserEntity entity = userRepository.findByActivationCode(activationCode);
        entity.setActive(true);
        entity.setActivationCode(null);
        userRepository.saveAndFlush(entity);return findById(entity.getId());

    }

    public List<User> getAvailableUsersForCommunication(Integer currentUserId) {
        return userRepository.findAllByIdNot(currentUserId).stream().map(u -> modelMapper.map(u, User.class)).collect(Collectors.toList());
    }
}
