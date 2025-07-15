package com.OrderNet.ProyWebIntegrado.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

  @GetMapping("/report")
  public ResponseEntity<byte[]> exportOrdersExcel(
      @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
      @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {

    try {
      ByteArrayInputStream in = orderService.generateOrdersExcel(startDate, endDate);
      HttpHeaders headers = new HttpHeaders();
      headers.add("Content-Disposition", "attachment; filename=ordenes_" + startDate + "_a_" + endDate + ".xlsx");

      return ResponseEntity.ok()
          .headers(headers)
          .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
          .body(in.readAllBytes());

    } catch (IOException e) {
      return ResponseEntity.internalServerError().build();
    }
  }

}
