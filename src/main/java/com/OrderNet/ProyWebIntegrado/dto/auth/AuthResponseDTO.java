package com.OrderNet.ProyWebIntegrado.dto.auth;

import com.OrderNet.ProyWebIntegrado.dto.user.UserDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthResponseDTO {
  private String accessToken;
  private String refreshToken;
  private UserDTO user;
}
