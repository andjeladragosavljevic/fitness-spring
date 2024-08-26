package com.example.fitnessspring.services;

import com.example.fitnessspring.models.entities.Participation;
import org.springframework.stereotype.Service;

@Service
public interface ParticipationService {
    public Participation participateInProgram(Participation participation);
}
