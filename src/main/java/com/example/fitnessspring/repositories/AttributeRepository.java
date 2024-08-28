package com.example.fitnessspring.repositories;

import com.example.fitnessspring.models.entities.AttributeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AttributeRepository extends JpaRepository<AttributeEntity, Integer> {
    public List<AttributeEntity> findAttributeEntitiesByCategory_Id (Integer categoryId);
    public AttributeEntity findAttributeEntityByName(String name);
    public List<AttributeEntity> findByNameIn(List<String> names);
}
