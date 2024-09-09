package com.example.fitnessspring.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendEmail(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setFrom("andjadragos@icloud.com");
        message.setSubject(subject);
        message.setText(body);

        mailSender.send(message);
    }

    public void sendActivationEmail(String to, String activationLink) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Account Activation");
        message.setText("Please click the following link to activate your account: " + activationLink);
        message.setFrom("andjadragos@icloud.com");
        mailSender.send(message);
    }
}
