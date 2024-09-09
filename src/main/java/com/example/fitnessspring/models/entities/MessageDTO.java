package com.example.fitnessspring.models.entities;

import lombok.Data;

@Data
public class MessageDTO {
        private Integer advisorId;
        private String content;
        private Integer senderId;

}
