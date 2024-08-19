package com.example.fitnessspring.services.impl;

import com.example.fitnessspring.models.entities.Attribute;
import com.example.fitnessspring.models.entities.AttributeEntity;
import com.example.fitnessspring.repositories.AttributeRepository;
import com.example.fitnessspring.services.AttributeService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AttributeImpl implements AttributeService {

    private final AttributeRepository attributeRepository;
    private final ModelMapper modelMapper;

    public AttributeImpl(AttributeRepository attributeRepository, ModelMapper modelMapper) {
        this.attributeRepository = attributeRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<Attribute> getAttributesByCategoryId(Integer id) {
        List<AttributeEntity> attributeEntities =  attributeRepository.findAttributeEntitiesByCategory_Id(id);
        return attributeEntities.stream().map(a -> modelMapper.map(a, Attribute.class)).collect(Collectors.toList());
    }
}
