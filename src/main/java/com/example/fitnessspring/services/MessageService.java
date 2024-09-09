package com.example.fitnessspring.services;

import com.example.fitnessspring.models.entities.Message;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MessageService {
    Message sendMessage(Message message);
    List<Message> getChatHistory(Integer senderId, Integer receiverId);
    public void sendMessageToAdvisor(Integer advisorId, String content, Integer userId);



    }
