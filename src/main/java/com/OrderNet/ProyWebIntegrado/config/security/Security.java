package com.OrderNet.ProyWebIntegrado.config.security;

import java.util.List;

import lombok.RequiredArgsConstructor;

import com.OrderNet.ProyWebIntegrado.persistence.model.enums.Permissions;
import com.OrderNet.ProyWebIntegrado.persistence.repository.UserRepository;
import com.OrderNet.ProyWebIntegrado.service.auth.JwtService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class Security {

  @Autowired
  private JwtService jwtService;
  @Autowired
  private UserRepository userRepository;

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity, UserDetailsService userDetailsService)
      throws Exception {
    JwtFilter jwtFilter = new JwtFilter(jwtService, userDetailsService);
    return httpSecurity
        .csrf(csrf -> csrf.disable())
        .cors(cors -> cors.configurationSource(corsConfigurationSource()))
        .authorizeHttpRequests(auth -> auth
            .requestMatchers("/auth/**").permitAll()

            .requestMatchers(HttpMethod.GET, "/user").hasAuthority(Permissions.READ_USER.toString())
            .requestMatchers(HttpMethod.GET, "/user/*").hasAuthority(Permissions.READ_USER.toString())
            .requestMatchers(HttpMethod.POST, "/user/create").hasAuthority(Permissions.CREATE_USER.toString())
            .requestMatchers(HttpMethod.PUT, "/user/update/*").hasAuthority(Permissions.UPDATE_USER.toString())
            .requestMatchers(HttpMethod.DELETE, "/user/delete/*").hasAuthority(Permissions.DELETE_USER.toString())

            .anyRequest().authenticated())
        .sessionManagement(
            sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authenticationProvider(authenticationProvider())
        .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
        .build();
  }

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
    return config.getAuthenticationManager();
  }

  @Bean
  public AuthenticationProvider authenticationProvider() {
    DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider(userDetailsService());
    daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
    return daoAuthenticationProvider;
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public UserDetailsService userDetailsService() {
    return username -> userRepository.findByEmail(username)
        .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
  }

  @Bean
  public CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration corsConfiguration = new CorsConfiguration();
    corsConfiguration
        .setAllowedOrigins(List.of("http://localhost:4321", "http://localhost:8080"));
    corsConfiguration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE"));
    corsConfiguration.setAllowedHeaders(List.of("Authorization", "Content-Type"));
    corsConfiguration.setAllowCredentials(true);
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", corsConfiguration);
    return source;
  }
}
