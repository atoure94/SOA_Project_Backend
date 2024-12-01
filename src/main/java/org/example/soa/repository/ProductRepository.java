package org.example.soa.repository;

import org.example.soa.bean.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Product findByRef(String ref);
    boolean existsByRef(String ref);
}

