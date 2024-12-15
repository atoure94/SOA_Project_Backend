package org.example.soa.controller;

import org.example.soa.bean.User;
import org.example.soa.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    // Récupérer tous les utilisateurs
    @GetMapping
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    // Créer un nouvel utilisateur
    @PostMapping
    public User createUser(@RequestBody User newUser) {
        return userRepository.save(newUser);
    }

    // Mettre à jour un utilisateur existant
    @PutMapping("/{id}")
    public User updateUsers(@PathVariable Long id, @RequestBody User userDetails) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setUsername(userDetails.getUsername());
        user.setPassword(userDetails.getPassword());
        user.setRole(userDetails.getRole());

        return userRepository.save(user);
    }

    // Supprimer un utilisateur
    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        userRepository.delete(user);
        return "User deleted successfully";
    }
}
