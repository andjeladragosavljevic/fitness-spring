package com.example.fitnessspring.models.entities;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Objects;

@Data
@Entity
@Table(name = "participation")
public class ParticipationEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;
    @Basic
    @Column(name = "participation_time", nullable = true)
    private Timestamp participationTime;
    @ManyToOne
    @JoinColumn(name = "User_id", referencedColumnName = "id", nullable = false)
    private UserEntity user;
    @ManyToOne
    @JoinColumn(name = "fitness_program_id", referencedColumnName = "id", nullable = false)
    private FitnessProgramEntity fitnessprogram;
    @ManyToOne
    @JoinColumn(name = "payment_method_id", referencedColumnName = "id", nullable = false)
    private PaymentmethodEntity paymentmethod;


    @PrePersist
    protected void onCreate() {
        participationTime = Timestamp.valueOf(LocalDateTime.now());
    }


}
