package com.example.fitnessspring.controllers;

import com.example.fitnessspring.models.entities.SubscriptionRequest;
import com.example.fitnessspring.services.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/subscriptions")
@CrossOrigin(origins = "http://localhost:4200")
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    public SubscriptionController(SubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;
    }



    @PostMapping
    public void subscribeToCategory(@RequestBody SubscriptionRequest subscriptionRequest) {
        subscriptionService.subscribeToCategory(
                subscriptionRequest.getUserId(),
                subscriptionRequest.getCategoryId(),
                subscriptionRequest.getEmail()
        );
    }
}
