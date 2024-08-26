package com.example.fitnessspring.repositories;

import com.example.fitnessspring.models.entities.PaymentmethodEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentMethodRepository extends JpaRepository<PaymentmethodEntity, Integer> {
}
