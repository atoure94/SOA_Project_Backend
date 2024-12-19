package org.example.soa.controller;

import org.example.soa.bean.Order;
import org.example.soa.bean.Product;
import org.example.soa.bean.User;
import org.example.soa.repository.OrderRepository;
import org.example.soa.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;
    private UserRepository userRepository;

    @GetMapping
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    // Récupérer une commande par ID
    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long id) {
        return orderRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Créer une nouvelle commande
    @PostMapping
    public ResponseEntity<?> createOrder(@RequestBody Order order) {

        try{


            Order savedOrder = orderRepository.save(order);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedOrder);
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while creating the product: " + e.getMessage());
        }

    }

    // Mettre à jour une commande existante
    @PutMapping("/{id}")
    public ResponseEntity<Order> updateOrder(@PathVariable Long id, @Valid @RequestBody Order orderDetails) {
        return orderRepository.findById(id).map(order -> {
            // Mise à jour des champs
            order.setName(orderDetails.getName());
            order.setAddress(orderDetails.getAddress());
            order.setEmail(orderDetails.getEmail());
            order.setTotal(orderDetails.getTotal());
            order.setProductNames(orderDetails.getProductNames());

            // Sauvegarde des modifications
            Order updatedOrder = orderRepository.save(order);
            return ResponseEntity.ok(updatedOrder);
        }).orElse(ResponseEntity.notFound().build());
    }

    // Supprimer une commande
    @DeleteMapping("/{id}")

    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        if (orderRepository.existsById(id)) { // Vérifie si l'ID existe
            orderRepository.deleteById(id);  // Supprime directement par ID
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
