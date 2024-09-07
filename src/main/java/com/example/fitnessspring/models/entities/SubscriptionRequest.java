package com.example.fitnessspring.models.entities;

import lombok.Data;

@Data
public class SubscriptionRequest {
    private Integer userId;
    private Integer categoryId;
    private String email;


}
