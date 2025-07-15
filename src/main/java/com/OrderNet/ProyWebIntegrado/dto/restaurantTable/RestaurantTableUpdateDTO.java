package com.OrderNet.ProyWebIntegrado.dto.restaurantTable;

import com.OrderNet.ProyWebIntegrado.persistence.model.enums.TableStatus;

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
  private String code;

  private TableStatus status;
  
  private Long waiterId;
}
