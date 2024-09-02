package com.example.fitnessspring.services;

import com.example.fitnessspring.models.entities.Participation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ParticipationService {
    public Participation participateInProgram(Participation participation);
    public List<Participation> getUserParticipations(Integer userId, boolean current);
}
