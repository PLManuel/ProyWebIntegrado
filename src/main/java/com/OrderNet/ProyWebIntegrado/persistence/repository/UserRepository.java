package com.OrderNet.ProyWebIntegrado.persistence.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.OrderNet.ProyWebIntegrado.persistence.model.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findByEmail(String email);
}
