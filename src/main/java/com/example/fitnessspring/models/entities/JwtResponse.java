package com.example.fitnessspring.models.entities;

import lombok.Data;

import java.util.List;

@Data
public class JwtResponse {
    private String token;
    private String type = "Bearer";
    private String username;
    private Integer id;

    public JwtResponse(String token, String username, Integer id) {
        this.token = token;
        this.username = username;
        this.id = id;
    }
}
