package org.example.soa.repository;

import org.example.soa.bean.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    // Trouver des commandes par email
    List<Order> findByEmail(String email);

    // Trouver des commandes par nom de produit
    List<Order> findByProductNamesContaining(String productName);

    // Trouver des commandes avec un total supérieur à un certain montant
    List<Order> findByTotalGreaterThan(Double total);

    // Trouver des commandes avec un total inférieur à un certain montant
    List<Order> findByTotalLessThan(Double total);

    // Trouver des commandes par nom
    List<Order> findByName(String name);

    // Trouver une commande par ID et email
    Order findByIdAndEmail(Long id, String email);
}
