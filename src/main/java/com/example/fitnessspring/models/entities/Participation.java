package com.example.fitnessspring.models.entities;


import lombok.Data;

import java.sql.Timestamp;


@Data
public class Participation {
    private Integer userId;
    private User user;
    private Integer fitnessprogramId;
    private Program fitnessprogram;
    private Integer paymentMethodId;
    private PaymentMethod paymentMethod;
    private Timestamp participationTime;

}
