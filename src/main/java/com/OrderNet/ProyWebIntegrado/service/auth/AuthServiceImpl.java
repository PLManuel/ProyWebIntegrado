package com.OrderNet.ProyWebIntegrado.service.auth;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.OrderNet.ProyWebIntegrado.dto.auth.AuthRequestDTO;
import com.OrderNet.ProyWebIntegrado.dto.auth.AuthResponseDTO;
import com.OrderNet.ProyWebIntegrado.dto.auth.RefreshTokenRequestDTO;
import com.OrderNet.ProyWebIntegrado.dto.user.UserDTO;
import com.OrderNet.ProyWebIntegrado.persistence.model.entities.User;
import com.OrderNet.ProyWebIntegrado.persistence.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

  private final AuthenticationManager authenticationManager;
  private final JwtService jwtService;
  private final UserRepository userRepository;

  @Override
  public AuthResponseDTO login(AuthRequestDTO request) {
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

    User user = userRepository.findByEmail(request.getEmail())
        .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

    String accessToken = jwtService.generateAccessToken(user);
    String refreshToken = jwtService.generateRefreshToken(user);

    UserDTO userDTO = UserDTO.builder()
        .id(user.getId())
        .name(user.getName())
        .email(user.getEmail())
        .role(user.getRole())
        .active(user.getActive())
        .build();

    return new AuthResponseDTO(accessToken, refreshToken, userDTO);
  }

  @Override
  public AuthResponseDTO refreshToken(RefreshTokenRequestDTO refreshTokenRequestDTO) {
    String refreshToken = refreshTokenRequestDTO.getRefreshToken();

    if (!jwtService.isTokenValid(refreshToken)) {
      throw new RuntimeException("Refresh token inválido o expirado");
    }

    String username = jwtService.extractUsername(refreshToken);
    User user = userRepository.findByEmail(username)
        .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

    String newAccessToken = jwtService.generateAccessToken(user);
    String newRefreshToken = jwtService.generateRefreshToken(user);

    UserDTO userDTO = UserDTO.builder()
        .id(user.getId())
        .name(user.getName())
        .email(user.getEmail())
        .role(user.getRole())
        .active(user.getActive())
        .build();

    return new AuthResponseDTO(newAccessToken, newRefreshToken, userDTO);
  }
}
