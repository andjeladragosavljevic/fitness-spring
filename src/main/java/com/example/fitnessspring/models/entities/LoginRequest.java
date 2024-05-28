package com.example.fitnessspring.models.entities;

import lombok.*;

@Data
public class LoginRequest {
    private String username;
    private String password;
    private String captcha;


}

