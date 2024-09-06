package com.example.fitnessspring.services.impl;

import com.example.fitnessspring.models.entities.Message;
import com.example.fitnessspring.models.entities.MessageEntity;
import com.example.fitnessspring.models.entities.UserEntity;
import com.example.fitnessspring.repositories.MessageRepository;
import com.example.fitnessspring.repositories.UserRepository;
import com.example.fitnessspring.services.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MessageServiceImpl implements MessageService {
    private final MessageRepository messageRepository;
    private final UserRepository userRepository;

    public MessageServiceImpl(MessageRepository messageRepository, UserRepository userRepository) {
        this.messageRepository = messageRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Message save(Message message) {
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
    public List<Message> findAllByReceiverId(Integer receiverId) {
        List<MessageEntity> messageEntities = messageRepository.findAllByReceiverId(receiverId);
        return messageEntities.stream().map(this::convertToDTO).collect(Collectors.toList());
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
