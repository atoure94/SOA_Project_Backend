package org.example.soa.repository;

import org.example.soa.bean.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    // Find all orders by user ID
    List<Order> findByUserUserId(Long userId);

    // Find all orders with a specific status
    List<Order> findByOrderStatus(String orderStatus);

    // Custom JPQL query: Find orders by product reference
    @Query("SELECT o FROM Order o WHERE o.product.ref = ?1")
    List<Order> findByProductRef(String productRef);
}
