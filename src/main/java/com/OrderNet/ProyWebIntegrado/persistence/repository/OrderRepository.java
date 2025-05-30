package com.OrderNet.ProyWebIntegrado.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.OrderNet.ProyWebIntegrado.persistence.model.entities.Order;
import java.util.List;
import com.OrderNet.ProyWebIntegrado.persistence.model.enums.OrderStatus;

public interface OrderRepository extends JpaRepository<Order, Long> {
  List<Order> findByStatus(OrderStatus status);
}
