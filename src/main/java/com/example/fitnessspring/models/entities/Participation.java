package com.example.fitnessspring.models.entities;


import lombok.Data;


@Data
public class Participation {
    private Integer userId;
    private Integer fitnessprogramId;
    private Program fitnessprogram;
    private Integer paymentMethodId;

}
