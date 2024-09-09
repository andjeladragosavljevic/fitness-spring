package com.example.fitnessspring.services;

import com.example.fitnessspring.models.entities.GoogleResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

@Service
public class CaptchaService {
    @Value("${recaptcha.secret}")
    private String recaptchaSecret;

    private static final Logger logger = LoggerFactory.getLogger(CaptchaService.class);

    private static final String GOOGLE_RECAPTCHA_VERIFY_URL = "https://www.google.com/recaptcha/api/siteverify";
    private final RestTemplate restTemplate = new RestTemplate();

    public boolean verifyCaptcha(String captchaResponse) {
        RestTemplate restTemplate = new RestTemplate();
        String url = String.format("%s?secret=%s&response=%s", GOOGLE_RECAPTCHA_VERIFY_URL, recaptchaSecret, captchaResponse);
        var googleResponse = restTemplate.getForObject(url, GoogleResponse.class);

        assert googleResponse != null;
        return googleResponse.isSuccess();
    }

}
