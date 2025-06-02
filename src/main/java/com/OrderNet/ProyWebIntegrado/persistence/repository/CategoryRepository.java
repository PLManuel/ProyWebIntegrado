package com.OrderNet.ProyWebIntegrado.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.OrderNet.ProyWebIntegrado.persistence.model.entities.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}
