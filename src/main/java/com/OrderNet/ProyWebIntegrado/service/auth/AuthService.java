package com.OrderNet.ProyWebIntegrado.service.auth;

import org.springframework.stereotype.Service;

import com.OrderNet.ProyWebIntegrado.dto.auth.AuthRequestDTO;
import com.OrderNet.ProyWebIntegrado.dto.auth.AuthResponseDTO;
import com.OrderNet.ProyWebIntegrado.dto.auth.RefreshTokenRequestDTO;

import jakarta.servlet.http.HttpServletRequest;

@Service
public interface AuthService {
  AuthResponseDTO login(AuthRequestDTO authRequestDTO);

  AuthResponseDTO refreshToken(RefreshTokenRequestDTO refreshTokenRequestDTO);

  void logout(HttpServletRequest request);
}
