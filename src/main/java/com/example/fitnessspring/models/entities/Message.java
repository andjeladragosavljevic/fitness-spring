package com.example.fitnessspring.models.entities;

import lombok.Data;

import java.sql.Timestamp;
@Data
public class Message {
    private Integer id;
    private String content;
    private Timestamp createdAt;
    private Boolean isRead;
    private Integer senderId;
    private Integer receiverId;
}
