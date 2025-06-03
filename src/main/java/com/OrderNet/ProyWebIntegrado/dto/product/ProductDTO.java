package com.OrderNet.ProyWebIntegrado.dto.product;

import java.math.BigDecimal;

import com.OrderNet.ProyWebIntegrado.dto.category.CategoryDTO;

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
public class ProductDTO {
  private Long id;
  private String name;
  private String description;
  private BigDecimal price;
  private Boolean available;
  private CategoryDTO categoryDTO;
}
