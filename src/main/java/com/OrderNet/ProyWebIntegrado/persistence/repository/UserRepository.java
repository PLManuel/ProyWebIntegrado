package com.OrderNet.ProyWebIntegrado.persistence.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.OrderNet.ProyWebIntegrado.persistence.model.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {
  // se podria usar com.google.common.base.Optional, pero actualmente esta en desuso
  Optional<User> findByEmail(String email);
}
