package com.OrderNet.ProyWebIntegrado.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.OrderNet.ProyWebIntegrado.persistence.model.entities.RestaurantTable;
import java.util.List;

public interface RestaurantTableRepository extends JpaRepository<RestaurantTable, Long> {
  List<RestaurantTable> findByNumber(Integer number);
}
