package com.example.fitnessspring.controllers;


import com.example.fitnessspring.models.entities.Program;
import com.example.fitnessspring.services.ProgramService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/programs")
@CrossOrigin(origins = "http://localhost:4200")
public class ProgramController {

    private final ProgramService programService;
    ProgramController(ProgramService programService){
        this.programService = programService;

    }

    @GetMapping
    public Page<Program> getPrograms(Pageable pageable) {
        return programService.findAll(pageable);
    }

    @PostMapping
    public ResponseEntity<Program> createProgram(@RequestBody Program program) {

        return ResponseEntity.ok(programService.save(program));
    }



    @GetMapping("/{id}")
    public ResponseEntity<Program> getProgram(@PathVariable Integer id) {
        return ResponseEntity.ok(programService.findById(id));
    }



}
