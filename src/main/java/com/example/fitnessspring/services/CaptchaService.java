package com.example.fitnessspring.services;

import com.example.fitnessspring.models.entities.GoogleResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@Service
public class CaptchaService {
    @Value("${recaptcha.secret}")
    private String recaptchaSecret;

    private static final Logger logger = LoggerFactory.getLogger(CaptchaService.class);

    private static final String GOOGLE_RECAPTCHA_VERIFY_URL = "https://www.google.com/recaptcha/api/siteverify";

    public boolean validateCaptcha(String captchaResponse) {

        RestTemplate restTemplate = new RestTemplate();
        String url = String.format("%s?secret=%s&response=%s", GOOGLE_RECAPTCHA_VERIFY_URL, recaptchaSecret, captchaResponse);
        GoogleResponse googleResponse = restTemplate.getForObject(url, GoogleResponse.class);

        assert googleResponse != null;
        return googleResponse.isSuccess();
//        System.out.println(captchaResponse);
//        RestTemplate restTemplate = new RestTemplate();
//
//        Map<String, String> body = new HashMap<>();
//        body.put("secret", recaptchaSecret);
//        body.put("response", captchaResponse);
//
//        logger.info("Sending CAPTCHA verification request to Google reCAPTCHA API: {}", body);
//        GoogleResponse googleResponse = restTemplate.postForObject(GOOGLE_RECAPTCHA_VERIFY_URL, body, GoogleResponse.class);
//        if (googleResponse != null) {
//            System.out.println("Success: " + googleResponse.isSuccess());
//            System.out.println("Hostname: " + googleResponse.getHostname());
//            System.out.println("Error Codes: " + Arrays.toString(googleResponse.getErrorCodes()));
//            return googleResponse.isSuccess();
//        }
//        return false;
    }
}
