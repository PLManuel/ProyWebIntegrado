package com.OrderNet.ProyWebIntegrado.config.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.OrderNet.ProyWebIntegrado.persistence.model.entities.User;
import com.OrderNet.ProyWebIntegrado.persistence.model.enums.Role;
import com.OrderNet.ProyWebIntegrado.persistence.repository.UserRepository;

@Configuration
public class InitialData {

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Bean
  public CommandLineRunner administrator(UserRepository userRepository) {
    User user = User.builder().name("Administrador").email("root@order.net").password(passwordEncoder.encode("ordernet")).role(Role.ADMINISTRATOR).build();

    return _ -> {
      if (userRepository.count() == 0) {
        userRepository.save(user);
        System.out.println("Initial data loaded");
      } else {
        System.out.println("A user already exists in the DB");
      }
    };
  }

}
