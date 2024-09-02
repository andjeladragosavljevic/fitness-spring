package com.example.fitnessspring.controllers;

import com.example.fitnessspring.models.entities.Participation;
import com.example.fitnessspring.models.entities.Program;
import com.example.fitnessspring.services.ParticipationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/participations")
@CrossOrigin(origins = "http://localhost:4200")
public class ParticipationController {
    private final ParticipationService participationService;

    public ParticipationController(ParticipationService participationService) {
        this.participationService = participationService;
    }

    @PostMapping
    public ResponseEntity<Participation> participate(@RequestBody Participation participation) {
       return ResponseEntity.ok(participationService.participateInProgram(participation));

    }


    @GetMapping ("/user/{id}")
    ResponseEntity<List<Participation>> getUserParticipations(@PathVariable Integer id){
        return  ResponseEntity.ok(participationService.getUserParticipations(id));
    }
}
