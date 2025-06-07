package com.OrderNet.ProyWebIntegrado.dto.order;

import java.util.List;

import jakarta.validation.constraints.NotEmpty;
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
public class OrderCreateDTO {

  @NotNull(message = "La mesa es obligatoria")
  private Long tableId;

  @NotNull(message = "El mozo es obligatorio")
  private Long waiterId;

  private String notes;

  @NotEmpty(message = "Debe haber al menos un producto en la orden")
  private List<OrderDetailCreateDTO> details;
}
