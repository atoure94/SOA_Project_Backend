package org.example.soa.controller;

import org.example.soa.bean.User;
import org.example.soa.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder; // Injecter PasswordEncoder

    // Récupérer tous les utilisateurs
    @GetMapping
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    // Créer un nouvel utilisateur
    @PostMapping("/register")
    public ResponseEntity<?> createUser(@RequestBody User newUser) {
        // Check if the username already exists
        if (userRepository.existsByUsername(newUser.getUsername())) {
            return ResponseEntity.badRequest().body("Username is already taken");
        }

        // Hash the password before saving
        String encodedPassword = passwordEncoder.encode(newUser.getPassword());
        newUser.setPassword(encodedPassword);

        // Save the new user to the database
        User savedUser = userRepository.save(newUser);

        // Return a response entity with the saved user and status 201 (Created)
        return ResponseEntity.status(201).body(savedUser);
    }

    // Mettre à jour un utilisateur existant
    @PutMapping("/{id}")
    public User updateUsers(@PathVariable Long id, @RequestBody User userDetails) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setUsername(userDetails.getUsername());
        // Ne pas hacher le mot de passe lors de la mise à jour si celui-ci n'est pas changé
        if (userDetails.getPassword() != null && !userDetails.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(userDetails.getPassword()));
        }
        user.setRole(userDetails.getRole());

        return userRepository.save(user);
    }

    // Supprimer un utilisateur
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        // Check if user exists
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Delete user
        userRepository.delete(user);

        return ResponseEntity.ok("User deleted successfully");
    }

}
