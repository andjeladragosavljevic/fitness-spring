package com.example.fitnessspring.models.entities;

import lombok.*;

@Data
public class RegisterRequest {

    private String username;
    private String password;
    private String email;
    private String captchaResponse;

}
