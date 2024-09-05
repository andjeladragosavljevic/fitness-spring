package com.example.fitnessspring.services.impl;

import com.example.fitnessspring.models.entities.Message;
import com.example.fitnessspring.services.MessageService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {
    @Override
    public void save(Message message) {

    }

    @Override
    public List<Message> findAllByReceiverId(Integer receiverId) {
        return List.of();
    }
}
