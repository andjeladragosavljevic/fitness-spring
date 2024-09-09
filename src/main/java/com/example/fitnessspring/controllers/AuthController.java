package com.example.fitnessspring.controllers;

import com.example.fitnessspring.models.entities.JwtResponse;
import com.example.fitnessspring.models.entities.LoginRequest;
import com.example.fitnessspring.models.entities.User;
import com.example.fitnessspring.services.CaptchaService;
import com.example.fitnessspring.services.EmailService;
import com.example.fitnessspring.services.JwtTokenUtil;
import com.example.fitnessspring.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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

    private final JwtTokenUtil jwtTokenUtil;

    private final EmailService emailService;
    public AuthController(UserService userService, CaptchaService captchaService, EmailService emailService, JwtTokenUtil jwtTokenUtil) {
        this.userService = userService;
        this.captchaService = captchaService;
        this.emailService = emailService;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        User user = userService.findByUsername(loginRequest.getUsername());
        if (user == null || !passwordEncoder().matches(loginRequest.getPassword(), user.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }

        if (!user.isActive()) {
            String activationCode = generateActivationCode();
            user.setActivationCode(activationCode);
            userService.save(user);


            String activationLink = "http://localhost:4200/activate-account?code=" + activationCode;
            emailService.sendActivationEmail(user.getEmail(), activationLink);

            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Account not activated. A new activation link has been sent.");
        }
        String token = jwtTokenUtil.generateToken(user);


        return ResponseEntity.ok(new JwtResponse(token, user.getUsername(), user.getId()));

    }

    @PostMapping("/registration")
    public ResponseEntity<?> register(@RequestBody User user) {

        if (!captchaService.verifyCaptcha(user.getCaptcha())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid CAPTCHA");
        }


        if (userService.existsByUsername(user.getUsername()) || userService.existsByEmail(user.getEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Username or Email already exists");
        }

        user.setPassword(passwordEncoder().encode(user.getPassword()));


        String activationCode = generateActivationCode();
        user.setActivationCode(activationCode);
        user.setActive(false);


        User savedUser = userService.save(user);

        String activationLink = "http://localhost:4200/activate-account?code=" + activationCode;
        emailService.sendActivationEmail(user.getEmail(), activationLink);

        return ResponseEntity.ok(user);
    }

    @GetMapping("/activate")
    public ResponseEntity<?> activateUser(@RequestParam("code") String code) {
        User user = userService.findByActivationCode(code);

        if (user == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid activation code");
        }

        user.setActive(true);
        user.setActivationCode(null);

        userService.save(user);

        return ResponseEntity.ok(user);
    }


    private String generateActivationCode() {
        return java.util.UUID.randomUUID().toString();
    }

    private PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
