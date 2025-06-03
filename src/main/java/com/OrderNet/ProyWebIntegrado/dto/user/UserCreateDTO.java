package com.OrderNet.ProyWebIntegrado.dto.user;

import com.OrderNet.ProyWebIntegrado.persistence.model.enums.Role;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
// import jakarta.validation.constraints.Pattern;
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
public class UserCreateDTO {

  @NotBlank(message = "El nombre es obligatorio")
  @Size(min = 3, max = 30, message = "El nombre debe tener entre 3 y 30 caracteres")
  private String name;

  @NotBlank(message = "El correo es obligatorio")
  @Email(message = "Debe ingresar un correo válido")
  private String email;

  // @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d).+$", message = "La
  // contraseña debe tener al menos una mayúscula, una minúscula y un número")
  @NotBlank(message = "La contraseña es obligatoria")
  @Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres")
  private String password;

  @NotNull(message = "El rol es obligatorio")
  private Role role;

  @Builder.Default
  private Boolean active = true;
}
