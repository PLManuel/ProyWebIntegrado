package com.OrderNet.ProyWebIntegrado.dto.restaurantTable;

import com.OrderNet.ProyWebIntegrado.dto.NullableField;
import com.OrderNet.ProyWebIntegrado.persistence.model.enums.TableStatus;

import jakarta.validation.constraints.Min;
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
public class RestaurantTableUpdateDTO {

  @Min(value = 1, message = "El numero de mesa minimo es de 1")
  private Integer number;

  private TableStatus status;
  
  private NullableField<Long> waiterId;
}
