package com.example.fitnessspring.models.entities;


import lombok.Data;


@Data
public class Participation {
    private Integer userId;
    private Integer programId;
    private Integer paymentMethodId;

}
