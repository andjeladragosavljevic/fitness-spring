package com.example.fitnessspring.controllers;


import com.example.fitnessspring.models.entities.Program;
import com.example.fitnessspring.models.entities.ProgramFilterDTO;
import com.example.fitnessspring.models.entities.User;
import com.example.fitnessspring.services.ProgramService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
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


    @GetMapping("/my-programs")
    public Page<Program> getMyPrograms(@RequestParam Map<String, String> filters, Pageable pageable) {
        Integer userId = getCurrentUserId();  // Metoda za dobijanje trenutno prijavljenog korisnika
        ProgramFilterDTO filterDTO = new ProgramFilterDTO(filters);
        return programService.filterPrograms(filterDTO, pageable, userId, true);

    }


    @GetMapping("/other-programs")
    public Page<Program> getOtherPrograms(@RequestParam Map<String, String> filters, Pageable pageable) {
        Integer userId = getCurrentUserId();  // Metoda za dobijanje trenutno prijavljenog korisnika
        ProgramFilterDTO filterDTO = new ProgramFilterDTO(filters);
        return programService.filterPrograms(filterDTO, pageable, userId, false);
    }

    private Integer getCurrentUserId() {
        // Implementacija za dobijanje ID-a trenutno prijavljenog korisnika
        return 31; // Placeholder
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

    @PutMapping("{id}")
    public ResponseEntity<Program> updateProgram(@PathVariable Integer id, @RequestBody Program program){
        return ResponseEntity.ok( programService.updateProgram(id, program));
    }




}
