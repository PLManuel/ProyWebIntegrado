package com.OrderNet.ProyWebIntegrado.dto.product;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
public class ProductCreateDTO {
  @NotBlank(message = "El nombre es obligatorio")
  private String name;

  private String description;

  @NotNull(message = "El precio es obligatorio")
  @DecimalMin(value = "0.0", inclusive = false, message = "El precio debe ser mayor a cero")
  private BigDecimal price;

  @Builder.Default
  private Boolean available = true;

  @NotNull(message = "La categoría es obligatoria")
  private Long categoryId;
}