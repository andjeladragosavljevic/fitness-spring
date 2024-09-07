package com.example.fitnessspring.services;

import com.example.fitnessspring.models.entities.SubscriptionEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SubscriptionService {
    List<SubscriptionEntity> getAllSubscriptions();
    void subscribeToCategory(Integer userId, Integer categoryId, String email);

}
