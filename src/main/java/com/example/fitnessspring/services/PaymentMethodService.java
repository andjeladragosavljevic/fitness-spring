package com.example.fitnessspring.services;

import com.example.fitnessspring.models.entities.PaymentMethod;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PaymentMethodService {
    public List<PaymentMethod> getPaymentMethods();
}
