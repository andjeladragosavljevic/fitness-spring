package com.example.fitnessspring.controllers;

import com.example.fitnessspring.models.entities.Admin;
import com.example.fitnessspring.models.entities.AdminEntity;
import com.example.fitnessspring.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/advisors")
@CrossOrigin(origins = "http://localhost:4200")
public class AdminController {
    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping
    public List<Admin> getAllAdvisors() {
        return adminService.findAllAdvisors();
    }
}
