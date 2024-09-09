package com.example.fitnessspring.services.impl;

import com.example.fitnessspring.models.entities.*;
import com.example.fitnessspring.repositories.ParticipationRepository;
import com.example.fitnessspring.repositories.PaymentMethodRepository;
import com.example.fitnessspring.repositories.ProgramRepository;
import com.example.fitnessspring.repositories.UserRepository;
import com.example.fitnessspring.services.LogService;
import com.example.fitnessspring.services.ParticipationService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

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
    private final LogService logService;

    public ParticipationServiceImpl(ParticipationRepository participationRepository, ModelMapper modelMapper, UserRepository userRepository, ProgramRepository programRepository, PaymentMethodRepository paymentMethodRepository, LogService logService) {
        this.participationRepository = participationRepository;
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
        this.programRepository = programRepository;
        this.paymentMethodRepository = paymentMethodRepository;
        this.logService = logService;
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

        logService.log("INFO", "Participation added: " + entity.getId());


        return modelMapper.map(entity, Participation.class);
    }

    @Override
    public List<Participation> getUserParticipations(Integer userId, boolean current) {
        LocalDate today = LocalDate.now();
        logService.log("INFO", "Fetched participations for user: " + userId);

        if (current) {
            return participationRepository.findByUserIdAndFitnessprogramEndDateAfter(userId, today)
                    .stream().map(this::convertToDto).collect(Collectors.toList());
        } else {
            return participationRepository.findByUserIdAndFitnessprogramEndDateBefore(userId, today)
                    .stream().map(this::convertToDto).collect(Collectors.toList());
        }



    }

    private Participation convertToDto(ParticipationEntity participation) {
        Participation dto = new Participation();
        Program program = new Program();
        FitnessProgramEntity fitnessProgramEntity = participation.getFitnessprogram();
        program.setName(fitnessProgramEntity.getName());
        program.setStartDate(fitnessProgramEntity.getStartDate());
        program.setEndDate(fitnessProgramEntity.getEndDate());
        program.setId(fitnessProgramEntity.getId());
        dto.setFitnessprogram(program);
        dto.setParticipationTime(participation.getParticipationTime());
        dto.setPaymentMethodId(participation.getPaymentmethod().getId());
        dto.setPaymentMethod(modelMapper.map(participation.getPaymentmethod(), PaymentMethod.class));

        return dto;
    }

}
