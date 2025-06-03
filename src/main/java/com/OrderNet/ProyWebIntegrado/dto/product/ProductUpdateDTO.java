package com.OrderNet.ProyWebIntegrado.dto.product;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
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
public class ProductUpdateDTO {
  private String name;
  private String description;

  @DecimalMin(value = "0.0", inclusive = false, message = "El precio debe ser mayor a cero")
  private BigDecimal price;
  
  private Boolean available;
  private Long categoryId;
}
