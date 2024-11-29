package org.example.soa.controller;

import org.example.soa.bean.User;
import org.example.soa.bean.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @PutMapping("/{id}")
    public User updateUsers(@PathVariable Long id, @RequestBody User userDetails) {
        User user  = userRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));

        user.setUsername(userDetails.getUsername());
        user.setPassword(userDetails.getPassword());
        user.setRole(userDetails.getRole());

        return userRepository.save(user);
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable Long id) {
        User user= userRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
        userRepository.delete(user);
        return "User deleted successfully";

    }

}
