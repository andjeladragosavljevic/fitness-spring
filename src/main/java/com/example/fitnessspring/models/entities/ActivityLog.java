package com.example.fitnessspring.models.entities;


import lombok.Data;

import java.sql.Timestamp;

@Data
public class ActivityLog {

    private Integer id;
    private Integer duration;
    private String difficultyLevel;
    private String exerciseType;
    private Integer result;
    private User user;
    private Integer userId;
    private Timestamp createdAt;

}
