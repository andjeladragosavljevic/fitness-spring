package com.example.fitnessspring.services.impl;

import com.example.fitnessspring.models.entities.*;
import com.example.fitnessspring.repositories.ParticipationRepository;
import com.example.fitnessspring.repositories.PaymentMethodRepository;
import com.example.fitnessspring.repositories.ProgramRepository;
import com.example.fitnessspring.repositories.UserRepository;
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

        System.out.print(userEntity.getId());

        if (participationRepository.existsByUserAndFitnessprogram(userEntity, programEntity)) {
            throw new RuntimeException("User already participating in this program");
        }

        entity.setUser(userEntity);
        entity.setFitnessprogram(programEntity);
        entity.setPaymentmethod(paymentmethodEntity);

        entity = participationRepository.saveAndFlush(entity);


        return modelMapper.map(entity, Participation.class);
    }

    @Override
    public List<Participation> getUserParticipations(Integer userId, boolean current) {
//        List<ParticipationEntity> participations = participationRepository.getParticipationEntitiesByUserId(userId);
//        return participations.stream()
//                .map(this::convertToDto)
//                .collect(Collectors.toList());

        LocalDate today = LocalDate.now();
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
//        dto.setParticipationDate(participation.getParticipationDate());
        dto.setPaymentMethodId(participation.getPaymentmethod().getId());
        dto.setPaymentMethod(modelMapper.map(participation.getPaymentmethod(), PaymentMethod.class));

        return dto;
    }

}
