package com.OrderNet.ProyWebIntegrado.persistence.repository;

import com.OrderNet.ProyWebIntegrado.persistence.model.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
  List<Product> findByAvailableTrue();
}
