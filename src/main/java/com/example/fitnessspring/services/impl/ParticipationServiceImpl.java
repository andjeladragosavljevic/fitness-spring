package com.example.fitnessspring.services.impl;

import com.example.fitnessspring.models.entities.*;
import com.example.fitnessspring.repositories.ParticipationRepository;
import com.example.fitnessspring.repositories.PaymentMethodRepository;
import com.example.fitnessspring.repositories.ProgramRepository;
import com.example.fitnessspring.repositories.UserRepository;
import com.example.fitnessspring.services.ParticipationService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class ParticipationServiceImpl implements ParticipationService {

    final
    ModelMapper modelMapper;

    final
    ParticipationRepository participationRepository;

    final
    PaymentMethodRepository paymentMethodRepository;

    final
    UserRepository userRepository;
    final
    ProgramRepository programRepository;

    public ParticipationServiceImpl(ParticipationRepository participationRepository, ModelMapper modelMapper, UserRepository userRepository, ProgramRepository programRepository, PaymentMethodRepository paymentMethodRepository) {
        this.participationRepository = participationRepository;
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
        this.programRepository = programRepository;
        this.paymentMethodRepository = paymentMethodRepository;
    }


    @Override
    public Participation participateInProgram(Participation participation) {
        ParticipationEntity entity = new ParticipationEntity();
        UserEntity userEntity = userRepository.findById(participation.getUserId()).orElseThrow(() -> new RuntimeException("User not found"));
        FitnessProgramEntity programEntity = programRepository.findById(participation.getFitnessprogramId()).orElseThrow(() -> new RuntimeException("Program not found"));
        PaymentmethodEntity paymentmethodEntity =  paymentMethodRepository.findById(participation.getPaymentMethodId()).orElseThrow(() -> new RuntimeException("Payment method not found"));


        if (participationRepository.existsByUserAndFitnessprogram(userEntity, programEntity)) {
            throw new RuntimeException("User already participating in this program");
        }

        entity.setUser(userEntity);
        entity.setFitnessprogram(programEntity);
        entity.setPaymentmethod(paymentmethodEntity);

        entity = participationRepository.saveAndFlush(entity);


        return modelMapper.map(entity, Participation.class);
    }
}
