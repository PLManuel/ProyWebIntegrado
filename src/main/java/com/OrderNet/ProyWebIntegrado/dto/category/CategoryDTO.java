package com.OrderNet.ProyWebIntegrado.dto.category;

import jakarta.validation.constraints.NotBlank;
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
public class CategoryDTO {
  
  private Long id;

  @NotBlank(message = "El nombre de la categoria es obligatorio")
  @Size(min = 1, message = "El nombre de la categoria debe de tener al menos 1 caracter")
  private String name;
}