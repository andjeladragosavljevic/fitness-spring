package com.example.fitnessspring.models.entities;

import lombok.*;


import java.sql.Timestamp;

@Data
@Getter
@Setter
public class Comment {

    private Integer id;

    private String content;

    private Timestamp createdAt;

    private Integer userId;

    private User user;

    private Integer fitnessProgramId;
}
