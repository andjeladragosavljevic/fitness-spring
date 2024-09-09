package com.example.fitnessspring.services.impl;

import com.example.fitnessspring.models.entities.Attribute;
import com.example.fitnessspring.models.entities.AttributeEntity;
import com.example.fitnessspring.repositories.AttributeRepository;
import com.example.fitnessspring.services.AttributeService;
import com.example.fitnessspring.services.LogService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AttributeImpl implements AttributeService {

    private final AttributeRepository attributeRepository;

    private final LogService logService;
    private final ModelMapper modelMapper;

    public AttributeImpl(AttributeRepository attributeRepository, LogService logService, ModelMapper modelMapper) {
        this.attributeRepository = attributeRepository;
        this.logService = logService;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<Attribute> getAttributesByCategoryId(Integer id) {
        List<AttributeEntity> attributeEntities =  attributeRepository.findAttributeEntitiesByCategory_Id(id);
        logService.log("INFO", "Fetched attributes for category: " + id );
        return attributeEntities.stream().map(a -> modelMapper.map(a, Attribute.class)).collect(Collectors.toList());
    }
}
