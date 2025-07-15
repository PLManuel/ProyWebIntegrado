package com.OrderNet.ProyWebIntegrado.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.OrderNet.ProyWebIntegrado.persistence.model.entities.RestaurantTable;
import com.OrderNet.ProyWebIntegrado.persistence.model.entities.User;

public interface RestaurantTableRepository extends JpaRepository<RestaurantTable, Long> {
    List<RestaurantTable> findByWaiter(User waiter);
}
