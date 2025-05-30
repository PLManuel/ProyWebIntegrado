package com.OrderNet.ProyWebIntegrado.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<ProductRepository, Long> {
  List<ProductRepository> findByAvailableTrue();
}
