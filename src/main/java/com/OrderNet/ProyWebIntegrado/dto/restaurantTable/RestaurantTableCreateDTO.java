package com.OrderNet.ProyWebIntegrado.dto.restaurantTable;

import com.OrderNet.ProyWebIntegrado.persistence.model.enums.TableStatus;

import jakarta.validation.constraints.NotBlank;
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

  @NotBlank(message = "El código de mesa es obligatorio")
  private String code;

  @Builder.Default
  private TableStatus status = TableStatus.AVAILABLE;
}