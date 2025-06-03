package com.OrderNet.ProyWebIntegrado.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.OrderNet.ProyWebIntegrado.dto.auth.AuthRequestDTO;
import com.OrderNet.ProyWebIntegrado.dto.auth.AuthResponseDTO;
import com.OrderNet.ProyWebIntegrado.dto.auth.RefreshTokenRequestDTO;
import com.OrderNet.ProyWebIntegrado.service.auth.AuthService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

  private final AuthService authService;

  @PostMapping("/login")
  public ResponseEntity<AuthResponseDTO> login(@RequestBody AuthRequestDTO authRequestDTO) {
    AuthResponseDTO authResponseDTO = authService.login(authRequestDTO);
    return ResponseEntity.ok(authResponseDTO);
  }

  @PostMapping("/refresh-token")
  public ResponseEntity<AuthResponseDTO> refreshToken(@RequestBody RefreshTokenRequestDTO request) {
    AuthResponseDTO authResponseDTO = authService.refreshToken(request);
    return ResponseEntity.ok(authResponseDTO);
  }

}
