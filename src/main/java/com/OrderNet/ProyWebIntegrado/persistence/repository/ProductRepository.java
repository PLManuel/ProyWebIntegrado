package com.OrderNet.ProyWebIntegrado.persistence.repository;

import java.util.List;
import java.util.Optional;
import com.OrderNet.ProyWebIntegrado.persistence.model.entities.Product;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

  @EntityGraph(attributePaths = "category")
  Optional<Product> findById(Long id);

  @EntityGraph(attributePaths = "category")
  List<Product> findAll();

}
