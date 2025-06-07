package com.OrderNet.ProyWebIntegrado.dto.order;

import com.OrderNet.ProyWebIntegrado.persistence.model.enums.OrderStatus;

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
public class OrderUpdateDTO {

  private Long tableId;

  @Size(min = 1, message = "Las notas no pueden estar vacías")
  private String notes;

  private OrderStatus status;

}
