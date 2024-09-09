package com.example.fitnessspring.services.impl;

import com.example.fitnessspring.models.entities.Admin;
import com.example.fitnessspring.models.entities.AdminEntity;
import com.example.fitnessspring.repositories.AdminRepository;
import com.example.fitnessspring.services.AdminService;
import com.example.fitnessspring.services.LogService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminServiceImpl implements AdminService {
    private final LogService logService;
    private final AdminRepository adminRepository;

    private final ModelMapper modelMapper;

    public AdminServiceImpl(LogService logService, AdminRepository adminRepository, ModelMapper modelMapper) {
        this.logService = logService;
        this.adminRepository = adminRepository;
        this.modelMapper = modelMapper;
    }

    public List<Admin> findAllAdvisors() {
        logService.log("INFO", "Fetched all advisors");
        return adminRepository.findByRole(AdminEntity.Role.Advisor).stream().map(x -> modelMapper.map(x, Admin.class)).collect(Collectors.toList());
    }
}
