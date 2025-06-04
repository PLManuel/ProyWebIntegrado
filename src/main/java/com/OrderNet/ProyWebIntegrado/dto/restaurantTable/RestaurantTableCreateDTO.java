package com.OrderNet.ProyWebIntegrado.dto.restaurantTable;

import com.OrderNet.ProyWebIntegrado.persistence.model.enums.TableStatus;

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
public class RestaurantTableCreateDTO {

  @NotNull(message = "El número de mesa es obligatorio")
  @Min(value = 1, message = "El numero de mesa minimo es de 1")
  private Integer number;

  @Builder.Default
  private TableStatus status = TableStatus.AVAILABLE;
}