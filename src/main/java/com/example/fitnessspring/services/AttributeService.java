package com.example.fitnessspring.services;

import com.example.fitnessspring.models.entities.Attribute;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AttributeService {
    public List<Attribute> getAttributesByCategoryId(Integer id);
}
