package com.example.fitnessspring.models.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@Data
public class User {

    private Integer id;

    private String firstName;

    private String lastName;

    private String city;

    private String username;

    private String password;

    private String avatar;

    private String email;

    private String captcha;

    private boolean isActive = false;

    private String activationCode;

    public User(Integer id, String firstName, String lastName, String email) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }
}
