package com.OrderNet.ProyWebIntegrado.service.restaurantTable;

import java.util.List;

import com.OrderNet.ProyWebIntegrado.dto.restaurantTable.RestaurantTableCreateDTO;
import com.OrderNet.ProyWebIntegrado.dto.restaurantTable.RestaurantTableDTO;
import com.OrderNet.ProyWebIntegrado.dto.restaurantTable.RestaurantTableUpdateDTO;

public interface RestaurantTableService {
  RestaurantTableDTO createRestaurantTable(RestaurantTableCreateDTO restaurantTableCreateDTO);

  RestaurantTableDTO getRestaurantTableById(Long id);

  List<RestaurantTableDTO> getAllRestaurantTables();

  RestaurantTableDTO updateRestaurantTable(Long id, RestaurantTableUpdateDTO restaurantTableUpdateDTO);

  void deleteRestaurantTable(Long id);
}