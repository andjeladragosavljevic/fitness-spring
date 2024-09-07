package com.example.fitnessspring.models.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "subscription")
public class SubscriptionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer subscriptionId;

    private Integer userId;
    private Integer categoryId;
    private String email;
    private Timestamp subscriptionDate;

    @PrePersist
    protected void onCreate() {
        subscriptionDate = Timestamp.valueOf(LocalDateTime.now());
    }


}
