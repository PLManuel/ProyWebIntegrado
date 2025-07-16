package com.OrderNet.ProyWebIntegrado.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.OrderNet.ProyWebIntegrado.persistence.model.entities.Order;
import com.OrderNet.ProyWebIntegrado.persistence.model.entities.RestaurantTable;
import com.OrderNet.ProyWebIntegrado.persistence.model.enums.OrderStatus;

public interface OrderRepository extends JpaRepository<Order, Long> {
  boolean existsByTableAndStatusNotAndIdNot(RestaurantTable table, OrderStatus status, Long excludeOrderId);
}
