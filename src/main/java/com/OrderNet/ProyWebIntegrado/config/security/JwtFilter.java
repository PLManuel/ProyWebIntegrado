package com.OrderNet.ProyWebIntegrado.config.security;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.OrderNet.ProyWebIntegrado.service.auth.JwtService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtFilter extends OncePerRequestFilter {

  private final JwtService jwtService;
  private final UserDetailsService userDetailsService;

  public JwtFilter(JwtService jwtService, UserDetailsService userDetailsService) {
    this.jwtService = jwtService;
    this.userDetailsService = userDetailsService;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    final String token = jwtService.getToken(request);

    if (token != null) {
      System.out.println("token received: " + token);

      if (jwtService.isTokenValid(token)) {
        String username = jwtService.extractUsername(token);
        System.out.println("user extracted from the token: " + username);

        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        if (userDetails != null) {
          UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null,
              userDetails.getAuthorities());
          SecurityContextHolder.getContext().setAuthentication(authToken);
          System.out.println("Authentication successful");
        }
      } else {
        System.out.println("Invalid or expired token");
      }
    } else {
      System.out.println("no token was received");
    }

    filterChain.doFilter(request, response);
  }

}
