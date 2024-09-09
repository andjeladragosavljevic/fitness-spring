package com.example.fitnessspring.controllers;

import com.example.fitnessspring.models.entities.User;
import com.example.fitnessspring.models.entities.UserEntity;
import com.example.fitnessspring.services.UserService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/available")
    public ResponseEntity<List<User>> getAvailableUsersForCommunication(@RequestParam Integer currentUserId) {
        return ResponseEntity.ok(userService.getAvailableUsersForCommunication(currentUserId));
    }



    @PutMapping("/profile/update")
    public ResponseEntity<?> updateProfile(@RequestBody User updatedUser) {
        User existingUser = userService.findById(updatedUser.getId());

        if (existingUser == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }


        existingUser.setFirstName(updatedUser.getFirstName());
        existingUser.setLastName(updatedUser.getLastName());
        existingUser.setCity(updatedUser.getCity());
        existingUser.setEmail(updatedUser.getEmail());


        if (!updatedUser.getPassword().isEmpty()) {
            existingUser.setPassword(passwordEncoder().encode(updatedUser.getPassword()));
        }
        existingUser.setAvatar(updatedUser.getAvatar());


        userService.save(existingUser);

        return ResponseEntity.ok(existingUser);
    }


    @GetMapping("/profile/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Integer id) {
        return ResponseEntity.ok(userService.findById(id));
    }




    private PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
