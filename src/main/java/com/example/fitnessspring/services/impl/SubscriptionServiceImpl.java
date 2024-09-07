package com.example.fitnessspring.services.impl;

import com.example.fitnessspring.models.entities.SubscriptionEntity;
import com.example.fitnessspring.repositories.CategoryRepository;
import com.example.fitnessspring.repositories.SubscriptionRepository;
import com.example.fitnessspring.services.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubscriptionServiceImpl implements SubscriptionService {
    private final SubscriptionRepository subscriptionRepository;



    public SubscriptionServiceImpl(SubscriptionRepository subscriptionRepository) {
        this.subscriptionRepository = subscriptionRepository;

    }

    @Override
    public List<SubscriptionEntity> getAllSubscriptions() {
        return subscriptionRepository.findAll();
    }

    @Override
    public void subscribeToCategory(Integer userId, Integer categoryId, String email) {
        SubscriptionEntity subscription = new SubscriptionEntity();
        subscription.setUserId(userId);
        subscription.setCategoryId(categoryId);
        subscription.setEmail(email);
        subscriptionRepository.save(subscription);
    }



}
