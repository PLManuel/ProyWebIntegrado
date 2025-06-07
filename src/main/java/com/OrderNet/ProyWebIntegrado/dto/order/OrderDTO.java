package com.OrderNet.ProyWebIntegrado.dto.order;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.OrderNet.ProyWebIntegrado.persistence.model.enums.OrderStatus;

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
public class OrderDTO {
  private Long id;
  private LocalDateTime createdAt;
  private OrderStatus status;
  private String notes;
  private BigDecimal total;
  private Long tableId;
  private String waiterName;
  private List<OrderDetailDTO> details;
}
