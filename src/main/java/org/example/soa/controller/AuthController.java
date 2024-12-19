package org.example.soa.controller;

import org.example.soa.bean.User;
import org.example.soa.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:5174")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody User user) {
        try {
            if (userRepository.existsByUsername(user.getUsername())) {
                return ResponseEntity.badRequest().body("Username is already taken");
            }
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            if (user.getRole() == null) {
                user.setRole(user.getRole());
            }
            userRepository.save(user);
            return ResponseEntity.ok("User registered successfully");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred during registration"+ e.getMessage());
        }
    }


    @PostMapping("/login")
    public ResponseEntity<Object> loginUser(@RequestBody User loginRequest) {
        // Recherche de l'utilisateur par son nom d'utilisateur
        User user = userRepository.findByUsername(loginRequest.getUsername());
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Username not found");
        }

        // Vérification du mot de passe
        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid password");
        }

          Map<String, Object> response = new HashMap<>();
        response.put("user", user);


        return ResponseEntity.ok(response);
    }




}

