package com.example.shopproject.repository;

import com.example.shopproject.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findProductById(Long id);
    List<Product> findProductsByName(String name);
}
