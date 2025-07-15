package com.OrderNet.ProyWebIntegrado.service.order;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import com.OrderNet.ProyWebIntegrado.dto.order.OrderCreateDTO;
import com.OrderNet.ProyWebIntegrado.dto.order.OrderDTO;
import com.OrderNet.ProyWebIntegrado.dto.order.OrderUpdateDTO;

public interface OrderService {
  OrderDTO createOrder(OrderCreateDTO orderCreateDTO);

  OrderDTO getOrderById(Long id);

  List<OrderDTO> getAllOrders();

  OrderDTO updateOrder(Long id, OrderUpdateDTO orderUpdateDTO);

  void deleteOrder(Long id);

  ByteArrayInputStream generateOrdersExcel(LocalDate startDate, LocalDate endDate) throws IOException;
}