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

import com.OrderNet.ProyWebIntegrado.dto.order.OrderCreateDTO;
import com.OrderNet.ProyWebIntegrado.dto.order.OrderDTO;
import com.OrderNet.ProyWebIntegrado.dto.order.OrderUpdateDTO;
import com.OrderNet.ProyWebIntegrado.service.order.OrderService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {
  private final OrderService orderService;

  @PostMapping("/create")
  public ResponseEntity<OrderDTO> createProduct(@Valid @RequestBody OrderCreateDTO orderCreateDTO) {
    OrderDTO createdProduct = orderService.createOrder(orderCreateDTO);
    return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
  }

  @GetMapping("/{id}")
  public ResponseEntity<OrderDTO> getOrderById(@PathVariable Long id) {
    OrderDTO order = orderService.getOrderById(id);
    return ResponseEntity.ok(order);
  }

  @GetMapping
  public ResponseEntity<List<OrderDTO>> getAllOrders() {
    List<OrderDTO> orders = orderService.getAllOrders();
    return ResponseEntity.ok(orders);
  }

  @PutMapping("/update/{id}")
  public ResponseEntity<OrderDTO> updateOrder(@PathVariable Long id,
      @Valid @RequestBody OrderUpdateDTO orderUpdateDTO) {
    OrderDTO updatedOrder = orderService.updateOrder(id, orderUpdateDTO);
    return ResponseEntity.ok(updatedOrder);
  }

  @DeleteMapping("/delete/{id}")
  public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
    orderService.deleteOrder(id);
    return ResponseEntity.noContent().build();
  }
}
