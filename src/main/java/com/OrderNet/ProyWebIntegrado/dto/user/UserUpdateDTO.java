package com.OrderNet.ProyWebIntegrado.dto.user;

import com.OrderNet.ProyWebIntegrado.persistence.model.enums.Role;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
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
public class UserUpdateDTO {

  @Size(min = 3, max = 30, message = "El nombre debe tener entre 3 y 30 caracteres")
  private String name;

  @Email(message = "Debe ingresar un correo válido")
  private String email;

  @Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres")
  private String password;

  private Role role;

  private Boolean active;
}
