package com.OrderNet.ProyWebIntegrado.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.OrderNet.ProyWebIntegrado.persistence.model.entities.OrderDetail;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {

}
