package com.example.fitnessspring.controllers;


import com.example.fitnessspring.models.entities.FilterDTO;
import com.example.fitnessspring.models.entities.FitnessProgramEntity;
import com.example.fitnessspring.models.entities.Program;
import com.example.fitnessspring.models.entities.User;
import com.example.fitnessspring.services.ProgramService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/programs")
@CrossOrigin(origins = "http://localhost:4200")
public class ProgramController {

    private final ProgramService programService;
    ProgramController(ProgramService programService){
        this.programService = programService;

    }

    @GetMapping
    public Page<Program> getPrograms(Pageable pageable, @AuthenticationPrincipal User user) {

        return programService.findProgramsByUserIdNot(pageable, 41);
    }


    @GetMapping("/my")
    public Page<Program> getMyPrograms(Pageable pageable, @AuthenticationPrincipal User user) {
//        return programService.getProgramsByUserId(pageable, user.getId());
        return programService.findProgramsByUserId(pageable, 41);

    }



    @GetMapping("/filter")
    public ResponseEntity<Page<Program>> filterPrograms(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String difficultyLevel,
            @RequestParam(required = false) String location,
            @RequestParam(required = false) String instructor,
            @RequestParam(required = false) LocalDate startDate,
            @RequestParam(required = false) LocalDate endDate,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice,
            @RequestParam(required = false) String specificAttributeName,
            @RequestParam(required = false) String specificAttributeValue,
            Pageable pageable) {

        Page<Program> programs = programService.filterPrograms(
                name, description, category, difficultyLevel, location, instructor,
                startDate, endDate, minPrice, maxPrice, specificAttributeName, specificAttributeValue, pageable);

        return ResponseEntity.ok(programs);
    }


    @PostMapping
    public ResponseEntity<Program> createProgram(@RequestBody Program program) {

        return ResponseEntity.ok(programService.save(program));
    }



    @GetMapping("/{id}")
    public ResponseEntity<Program> getProgram(@PathVariable Integer id) {
        return ResponseEntity.ok(programService.findById(id));
    }

    @DeleteMapping("/{id}")
    public void deleteProgram(@PathVariable Integer id){
        programService.deleteProgram(id);
    }


}
