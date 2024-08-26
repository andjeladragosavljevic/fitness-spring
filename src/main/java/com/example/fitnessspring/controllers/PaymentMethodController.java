package com.example.fitnessspring.controllers;

import com.example.fitnessspring.models.entities.PaymentMethod;
import com.example.fitnessspring.models.entities.PaymentmethodEntity;
import com.example.fitnessspring.services.PaymentMethodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/payment-methods")
@CrossOrigin(origins = "http://localhost:4200")
public class PaymentMethodController {

    final
    PaymentMethodService paymentMethodService;

    public PaymentMethodController(PaymentMethodService paymentMethodService) {
        this.paymentMethodService = paymentMethodService;
    }

    @GetMapping
    public List<PaymentMethod> getPaymentMethods(){
        return paymentMethodService.getPaymentMethods();
    }
}
