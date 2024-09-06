package com.example.fitnessspring.controllers;

import com.example.fitnessspring.models.entities.Message;
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

    @PostMapping("/send")
    public ResponseEntity<Message> sendMessage(@RequestBody Message message) {
        Message sentMessage = messageService.save(message);
        return new ResponseEntity<>(sentMessage, HttpStatus.OK);
    }

    @GetMapping("/{receiverId}")
    public ResponseEntity<List<Message>> getMessagesBetweenUsers(
           @PathVariable Integer receiverId) {
        List<Message> messages = messageService.findAllByReceiverId(receiverId);
        return new ResponseEntity<>(messages, HttpStatus.OK);
    }
}
