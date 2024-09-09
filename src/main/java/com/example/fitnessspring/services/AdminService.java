package com.example.fitnessspring.services;

import com.example.fitnessspring.models.entities.Admin;
import com.example.fitnessspring.models.entities.AdminEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AdminService {

    public List<Admin> findAllAdvisors() ;
}
