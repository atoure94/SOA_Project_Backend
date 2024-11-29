package org.example.soa.bean;

import jakarta.persistence.*;

@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private double price;
    private String ref; // Correction : ref est maintenant une chaîne de caractères.

    // Getters et setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public String getRef() { // Correction : ref est maintenant une chaîne de caractères.
        return ref;
    }
    public void setRef(String ref) { // Correction : ref est maintenant une chaîne de caractères.
        this.ref = ref;
    }
}
