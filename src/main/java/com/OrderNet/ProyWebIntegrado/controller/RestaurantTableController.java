package com.OrderNet.ProyWebIntegrado.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.OrderNet.ProyWebIntegrado.dto.restaurantTable.RestaurantTableCreateDTO;
import com.OrderNet.ProyWebIntegrado.dto.restaurantTable.RestaurantTableDTO;
import com.OrderNet.ProyWebIntegrado.dto.restaurantTable.RestaurantTableUpdateDTO;
import com.OrderNet.ProyWebIntegrado.service.restaurantTable.RestaurantTableService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/restaurant-table")
@RequiredArgsConstructor
public class RestaurantTableController {
  private final RestaurantTableService restaurantTableService;

  @PostMapping("/create")
  public ResponseEntity<RestaurantTableDTO> createRestaurantTable(@Valid @RequestBody RestaurantTableCreateDTO restaurantTableCreateDTO) {
    RestaurantTableDTO createdRestaurantTable = restaurantTableService.createRestaurantTable(restaurantTableCreateDTO);
    return ResponseEntity.status(HttpStatus.CREATED).body(createdRestaurantTable);
  }

  @GetMapping("/{id}")
  public ResponseEntity<RestaurantTableDTO> getRestaurantTableById(@PathVariable Long id) {
    RestaurantTableDTO restaurantTable = restaurantTableService.getRestaurantTableById(id);
    return ResponseEntity.ok(restaurantTable);
  }

  @GetMapping
  public ResponseEntity<List<RestaurantTableDTO>> getAllRestaurantTables() {
    List<RestaurantTableDTO> restaurantTables = restaurantTableService.getAllRestaurantTables();
    return ResponseEntity.ok(restaurantTables);
  }

  @PutMapping("/update/{id}")
  public ResponseEntity<RestaurantTableDTO> updateProduct(@PathVariable Long id,
      @Valid @RequestBody RestaurantTableUpdateDTO restaurantTableUpdateDTO) {
    RestaurantTableDTO updatedRestaurantTable = restaurantTableService.updateRestaurantTable(id, restaurantTableUpdateDTO);
    return ResponseEntity.ok(updatedRestaurantTable);
  }

  @DeleteMapping("/delete/{id}")
  public ResponseEntity<Void> deleteRestaurantTable(@PathVariable Long id) {
    restaurantTableService.deleteRestaurantTable(id);
    return ResponseEntity.noContent().build();
  }
}
