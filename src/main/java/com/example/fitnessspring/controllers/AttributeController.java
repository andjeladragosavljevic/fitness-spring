package com.example.fitnessspring.controllers;

import com.example.fitnessspring.models.entities.Attribute;
import com.example.fitnessspring.services.AttributeService;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/attributes")
public class AttributeController {
    private final AttributeService attributeService;
    public AttributeController(AttributeService attributeService) {
        this.attributeService = attributeService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<Attribute>> getAttributesByCategoryId(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(attributeService.getAttributesByCategoryId(id));

    }
}
