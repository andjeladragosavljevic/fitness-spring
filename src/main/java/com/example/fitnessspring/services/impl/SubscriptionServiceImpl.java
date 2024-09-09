package com.example.fitnessspring.services.impl;

import com.example.fitnessspring.models.entities.SubscriptionEntity;
import com.example.fitnessspring.repositories.SubscriptionRepository;
import com.example.fitnessspring.services.LogService;
import com.example.fitnessspring.services.SubscriptionService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubscriptionServiceImpl implements SubscriptionService {
    private final SubscriptionRepository subscriptionRepository;
    private final LogService logService;


    public SubscriptionServiceImpl(SubscriptionRepository subscriptionRepository, LogService logService) {
        this.subscriptionRepository = subscriptionRepository;

        this.logService = logService;
    }

    @Override
    public List<SubscriptionEntity> getAllSubscriptions() {
        logService.log("INFO", "Fetched all subscriptions " );

        return subscriptionRepository.findAll();
    }

    @Override
    public void subscribeToCategory(Integer userId, Integer categoryId, String email) {
        SubscriptionEntity subscription = new SubscriptionEntity();
        subscription.setUserId(userId);
        subscription.setCategoryId(categoryId);
        subscription.setEmail(email);

        logService.log("INFO", "Subscription added ");

        subscriptionRepository.save(subscription);
    }



}
