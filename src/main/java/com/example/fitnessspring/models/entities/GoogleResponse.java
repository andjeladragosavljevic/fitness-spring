package com.example.fitnessspring.models.entities;


import lombok.*;

import java.util.List;

@Data
public class GoogleResponse {
    private boolean success;
    private String hostname;
    private String[] errorCodes;
}
