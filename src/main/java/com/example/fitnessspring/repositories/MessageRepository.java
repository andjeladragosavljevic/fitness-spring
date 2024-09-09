package com.example.fitnessspring.repositories;

import com.example.fitnessspring.models.entities.MessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<MessageEntity, Integer> {
    List<MessageEntity> findBySenderIdAndReceiverIdOrReceiverIdAndSenderId(Integer senderId, Integer receiverId, Integer receiverIdAlt, Integer senderIdAlt);
}
