package com.OrderNet.ProyWebIntegrado.dto.order;

import jakarta.validation.constraints.Min;
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
public class OrderDetailCreateDTO {

  @NotNull(message = "El producto es obligatorio")
  private Long productId;

  @NotNull(message = "La cantidad es obligatoria")
  @Min(value = 1, message = "La cantidad mínima es 1")
  private Integer quantity;
}