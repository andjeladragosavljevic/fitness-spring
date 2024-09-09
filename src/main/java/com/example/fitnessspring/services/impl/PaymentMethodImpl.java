package com.example.fitnessspring.services.impl;

import com.example.fitnessspring.models.entities.PaymentMethod;
import com.example.fitnessspring.repositories.PaymentMethodRepository;
import com.example.fitnessspring.services.LogService;
import com.example.fitnessspring.services.PaymentMethodService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PaymentMethodImpl implements PaymentMethodService {
    final
    PaymentMethodRepository paymentMethodRepository;

    private final LogService logService;
    final
    ModelMapper modelMapper;

    public PaymentMethodImpl(PaymentMethodRepository paymentMethodRepository, LogService logService, ModelMapper modelMapper) {
        this.paymentMethodRepository = paymentMethodRepository;
        this.logService = logService;
        this.modelMapper = modelMapper;
    }


    @Override
    public List<PaymentMethod> getPaymentMethods() {
        logService.log("INFO", "Fetched all payment methods: ");

        return  paymentMethodRepository.findAll().stream().map(pm -> modelMapper.map(
                pm, PaymentMethod.class)
        ).collect(Collectors.toList());
    }
}
