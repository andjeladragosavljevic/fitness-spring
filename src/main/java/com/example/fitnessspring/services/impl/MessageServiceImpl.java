package com.example.fitnessspring.services.impl;

import com.example.fitnessspring.models.entities.*;
import com.example.fitnessspring.repositories.AdminRepository;
import com.example.fitnessspring.repositories.MessageRepository;
import com.example.fitnessspring.repositories.MessageToAdvisorRepository;
import com.example.fitnessspring.repositories.UserRepository;
import com.example.fitnessspring.services.LogService;
import com.example.fitnessspring.services.MessageService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MessageServiceImpl implements MessageService {
    private final MessageRepository messageRepository;
    private final MessageToAdvisorRepository messageToAdvisorRepository;
    private final UserRepository userRepository;
    private final LogService logService;
    private final AdminRepository adminRepository;

    public MessageServiceImpl(MessageRepository messageRepository, MessageToAdvisorRepository messageToAdvisorRepository, UserRepository userRepository, LogService logService, AdminRepository adminRepository) {
        this.messageRepository = messageRepository;
        this.messageToAdvisorRepository = messageToAdvisorRepository;
        this.userRepository = userRepository;
        this.logService = logService;
        this.adminRepository = adminRepository;
    }



    public void sendMessageToAdvisor(Integer advisorId, String content, Integer userId) {
        logService.log("INFO", "Attempting to send a message from user " + userId + " to advisor " + advisorId);

        AdminEntity advisor = adminRepository.findById(advisorId)
                .orElseThrow(() -> new RuntimeException("Advisor not found"));

        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        MessagetoadvisorEntity entity = new MessagetoadvisorEntity();
        entity.setContent(content);
        entity.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        entity.setAdmin(advisor);
        entity.setUser(user);
        entity.setIsRead(false);

        messageToAdvisorRepository.save(entity);
    }


    @Override
    public Message sendMessage(Message message) {
        logService.log("INFO", "Attempting to send a message from user " + message.getSenderId() + " to user " + message.getReceiverId());

        MessageEntity messageEntity = new MessageEntity();
        messageEntity.setContent(message.getContent());
        messageEntity.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        messageEntity.setIsRead(false);

        UserEntity sender = userRepository.findById(message.getSenderId())
                .orElseThrow(() -> new RuntimeException("Sender not found"));
        UserEntity receiver = userRepository.findById(message.getReceiverId())
                .orElseThrow(() -> new RuntimeException("Receiver not found"));

        messageEntity.setSender(sender);
        messageEntity.setReceiver(receiver);

        messageRepository.save(messageEntity);

        message.setId(messageEntity.getId());
        message.setCreatedAt(messageEntity.getCreatedAt());

        return message;
    }

    @Override
    public List<Message> getChatHistory(Integer senderId, Integer receiverId) {
        logService.log("INFO", "Fetching chat history between user " + senderId + " and user " + receiverId);

        return messageRepository.findBySenderIdAndReceiverIdOrReceiverIdAndSenderId(senderId, receiverId, senderId, receiverId).stream().map(this::convertToDTO).collect(Collectors.toList());
    }
    private Message convertToDTO(MessageEntity messageEntity) {
        Message dto = new Message();
        dto.setId(messageEntity.getId());
        dto.setContent(messageEntity.getContent());
        dto.setCreatedAt(messageEntity.getCreatedAt());
        dto.setIsRead(messageEntity.getRead());
        dto.setSenderId(messageEntity.getSender().getId());
        dto.setReceiverId(messageEntity.getReceiver().getId());
        return dto;
    }
}
