package org.example.soa.controller;

import org.example.soa.bean.Order;
import org.example.soa.bean.Product;
import org.example.soa.bean.User;
import org.example.soa.repository.OrderRepository;
import org.example.soa.repository.ProductRepository;
import org.example.soa.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    // Get all orders
    @GetMapping
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    // Get a specific order by ID
    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long id) {
        return orderRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Create a new order
    @PostMapping
    public ResponseEntity<Order> createOrder(@Valid @RequestBody Order order) {
        // Validate User
        User user = userRepository.findById(order.getUser().getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Validate Product
        Product product = productRepository.findById(order.getProduct().getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // Associate user and product with the order
        order.setUser(user);
        order.setProduct(product);

        // Save and return the order
        Order savedOrder = orderRepository.save(order);
        return ResponseEntity.ok(savedOrder);
    }

    // Update an existing order
    @PutMapping("/{id}")
    public ResponseEntity<Order> updateOrder(@PathVariable Long id, @Valid @RequestBody Order orderDetails) {
        return orderRepository.findById(id).map(order -> {
            // Validate User
            User user = userRepository.findById(orderDetails.getUser().getUserId())
                    .orElseThrow(() -> new RuntimeException("User not found"));

            // Validate Product
            Product product = productRepository.findById(orderDetails.getProduct().getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));

            // Update fields
            order.setUser(user);
            order.setProduct(product);
            order.setQuantity(orderDetails.getQuantity());
            order.setOrderDate(orderDetails.getOrderDate());
            order.setOrderStatus(orderDetails.getOrderStatus());

            // Save and return the updated order
            Order updatedOrder = orderRepository.save(order);
            return ResponseEntity.ok(updatedOrder);
        }).orElse(ResponseEntity.notFound().build());
    }

}
