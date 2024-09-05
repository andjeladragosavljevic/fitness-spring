package com.example.fitnessspring.services;

import com.example.fitnessspring.models.entities.Message;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MessageService {
    public void save(Message message);
    public List<Message> findAllByReceiverId(Integer receiverId);

}
