package com.example.fitnessspring.controllers;

import com.example.fitnessspring.models.entities.LoginRequest;
import com.example.fitnessspring.models.entities.User;
import com.example.fitnessspring.services.CaptchaService;
import com.example.fitnessspring.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {

    private final UserService userService;

    private final CaptchaService captchaService;
    public AuthController(UserService userService, CaptchaService captchaService) {
        this.userService = userService;
        this.captchaService = captchaService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        if (captchaService.validateCaptcha(loginRequest.getCaptcha())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid CAPTCHA");
        }
        User user = userService.findByUsername(loginRequest.getUsername());
        if (user != null && passwordEncoder().matches(loginRequest.getPassword(), user.getPassword())) {
            return ResponseEntity.ok("Login successful");
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
    }

    @PostMapping("/registration")
    public ResponseEntity<?> register(@RequestBody User user) {

        if (!captchaService.validateCaptcha(user.getCaptcha())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid CAPTCHA");
        }

        user.setPassword(passwordEncoder().encode(user.getPassword()));

        return ResponseEntity.ok(userService.save(user));
    }

    private PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
