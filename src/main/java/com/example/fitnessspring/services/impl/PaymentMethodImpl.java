package com.example.fitnessspring.services.impl;

import com.example.fitnessspring.models.entities.PaymentMethod;
import com.example.fitnessspring.repositories.PaymentMethodRepository;
import com.example.fitnessspring.services.PaymentMethodService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PaymentMethodImpl implements PaymentMethodService {
    final
    PaymentMethodRepository paymentMethodRepository;
    final
    ModelMapper modelMapper;

    public PaymentMethodImpl(PaymentMethodRepository paymentMethodRepository, ModelMapper modelMapper) {
        this.paymentMethodRepository = paymentMethodRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    public List<PaymentMethod> getPaymentMethods() {
        System.out.println(paymentMethodRepository.findAll());
        return  paymentMethodRepository.findAll().stream().map(pm -> modelMapper.map(
                pm, PaymentMethod.class)
        ).collect(Collectors.toList());
    }
}
