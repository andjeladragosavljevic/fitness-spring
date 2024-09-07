package com.example.fitnessspring.services;

import com.example.fitnessspring.models.entities.FitnessProgramEntity;
import com.example.fitnessspring.models.entities.SubscriptionEntity;
import com.example.fitnessspring.repositories.ProgramRepository;
import com.example.fitnessspring.repositories.SubscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class DailyEmailService {

    private final SubscriptionRepository subscriptionRepository;

    private final ProgramRepository fitnessProgramRepository;

    private final EmailService emailService;

    public DailyEmailService(SubscriptionRepository subscriptionRepository, ProgramRepository fitnessProgramRepository, EmailService emailService) {
        this.subscriptionRepository = subscriptionRepository;
        this.fitnessProgramRepository = fitnessProgramRepository;
        this.emailService = emailService;
    }

    //
    //@Scheduled(cron = "0 0 9 * * ?") // Svaki dan u 9:00
    @Scheduled(cron = "0 35 18 * * ?")
    public void sendDailyEmails() {
        List<SubscriptionEntity> subscriptions = subscriptionRepository.findAll();

        for (SubscriptionEntity subscription : subscriptions) {
            List<FitnessProgramEntity> newPrograms = fitnessProgramRepository
                    .findNewProgramsByCategory(subscription.getCategoryId(), LocalDateTime.now().minusDays(1));

            if (!newPrograms.isEmpty()) {
                String emailBody = createEmailBody(newPrograms);
                emailService.sendEmail(subscription.getEmail(), "New Fitness Programs", emailBody);
            }
        }
    }

    private String createEmailBody(List<FitnessProgramEntity> programs) {
        StringBuilder sb = new StringBuilder();
        sb.append("Here are the new fitness programs for today:\n");
        for (FitnessProgramEntity program : programs) {
            sb.append(program.getName()).append(" - ").append(program.getDescription()).append("\n");
        }
        return sb.toString();
    }
}
