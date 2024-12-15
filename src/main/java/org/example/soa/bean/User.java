package org.example.soa.bean;

import jakarta.persistence.*;

@Entity
@Table(name = "app_users") // Renamed for consistency
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(nullable = false, length = 50, unique = true) // Added length and uniqueness for username
    private String username;

    @Column(nullable = false)
    private String password; // Consider storing a hashed password for security

    @Column(nullable = false, length = 20)
    private String role;

    // Getters and Setters
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
