package com.example.fitnessspring.controllers;

import com.example.fitnessspring.models.entities.Message;
import com.example.fitnessspring.models.entities.MessageDTO;
import com.example.fitnessspring.services.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
@CrossOrigin(origins = "http://localhost:4200")
public class MessageController {
    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping("/chat/{senderId}/{receiverId}")
    public List<Message> getChatHistory(@PathVariable Integer senderId, @PathVariable Integer receiverId) {
        return messageService.getChatHistory(senderId, receiverId);
    }

    @PostMapping("/send")
    public ResponseEntity<String> sendMessageToAdvisor(@RequestBody MessageDTO messageDTO) {
        messageService.sendMessageToAdvisor(messageDTO.getAdvisorId(), messageDTO.getContent(), messageDTO.getSenderId());
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping
    public Message sendMessage(@RequestBody Message message) {
        return messageService.sendMessage(message);
    }
}
