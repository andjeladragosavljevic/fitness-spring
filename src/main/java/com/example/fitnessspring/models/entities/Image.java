package com.example.fitnessspring.models.entities;

import jakarta.persistence.*;
import lombok.*;

@Data
public class Image {

    private Integer id;

    private String url;

    private FitnessProgramEntity fitnessProgram;



}
