package com.example.fitnessspring.models.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

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


}
