package com.example.fitnessspring.models.entities;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@Data
public class Image {

    private Integer id;

    private String url;

    private FitnessProgramEntity fitnessProgram;

    public Image(String url, Integer id) {
        this.url = url;
        this.id = id;
    }
}
