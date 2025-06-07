package com.OrderNet.ProyWebIntegrado.service.order;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

import com.OrderNet.ProyWebIntegrado.dto.order.OrderCreateDTO;
import com.OrderNet.ProyWebIntegrado.dto.order.OrderDTO;
import com.OrderNet.ProyWebIntegrado.dto.order.OrderDetailCreateDTO;
import com.OrderNet.ProyWebIntegrado.dto.order.OrderDetailDTO;
import com.OrderNet.ProyWebIntegrado.dto.order.OrderUpdateDTO;
import com.OrderNet.ProyWebIntegrado.dto.product.ProductDTO;
import com.OrderNet.ProyWebIntegrado.persistence.model.entities.Order;
import com.OrderNet.ProyWebIntegrado.persistence.model.entities.OrderDetail;
import com.OrderNet.ProyWebIntegrado.persistence.model.entities.Product;
import com.OrderNet.ProyWebIntegrado.persistence.model.entities.RestaurantTable;
import com.OrderNet.ProyWebIntegrado.persistence.model.entities.User;
import com.OrderNet.ProyWebIntegrado.persistence.model.enums.OrderStatus;
// import com.OrderNet.ProyWebIntegrado.persistence.model.enums.Role;
// import com.OrderNet.ProyWebIntegrado.persistence.repository.OrderDetailRepository;
import com.OrderNet.ProyWebIntegrado.persistence.repository.OrderRepository;
import com.OrderNet.ProyWebIntegrado.persistence.repository.ProductRepository;
import com.OrderNet.ProyWebIntegrado.persistence.repository.RestaurantTableRepository;
import com.OrderNet.ProyWebIntegrado.persistence.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

  private final OrderRepository orderRepository;
  // private final OrderDetailRepository orderDetailRepository;
  private final RestaurantTableRepository restaurantTableRepository;
  private final UserRepository userRepository;
  private final ProductRepository productRepository;

  private OrderDTO toDTO(Order order) {
    return OrderDTO.builder()
        .id(order.getId())
        .createdAt(order.getCreatedAt())
        .status(order.getStatus())
        .notes(order.getNotes())
        .total(order.getTotal())
        .tableId(order.getTable().getId())
        .waiterName(order.getWaiter().getName())
        .details(order.getDetails().stream()
            .map(this::toOrderDetailDTO)
            .toList())
        .build();
  }

  private OrderDetailDTO toOrderDetailDTO(OrderDetail detail) {
    return OrderDetailDTO.builder()
        .id(detail.getId())
        .quantity(detail.getQuantity())
        .product(toProductDTO(detail.getProduct()))
        .build();
  }

  private ProductDTO toProductDTO(Product product) {
    return ProductDTO.builder()
        .id(product.getId())
        .name(product.getName())
        .price(product.getPrice())
        .build();
  }

  @Override
  public OrderDTO createOrder(OrderCreateDTO orderCreateDTO) {
    RestaurantTable table = restaurantTableRepository.findById(orderCreateDTO.getTableId())
        .orElseThrow(() -> new NoSuchElementException("Mesa no encontrada con ID: " + orderCreateDTO.getTableId()));

    User waiter = userRepository.findById(orderCreateDTO.getWaiterId())
        .orElseThrow(() -> new NoSuchElementException("Mozo no encontrado con ID: " + orderCreateDTO.getWaiterId()));

    if (CollectionUtils.isEmpty(orderCreateDTO.getDetails())) {
      throw new IllegalArgumentException("Debe haber al menos un producto");
    }

    String notes = ObjectUtils.defaultIfNull(orderCreateDTO.getNotes(), "");

    Order order = Order.builder()
        .table(table)
        .waiter(waiter)
        .status(OrderStatus.PENDING)
        .notes(notes)
        .total(BigDecimal.ZERO)
        .build();

    List<OrderDetail> details = new ArrayList<>();
    BigDecimal total = BigDecimal.ZERO;

    for (OrderDetailCreateDTO item : orderCreateDTO.getDetails()) {
      Product product = productRepository.findById(item.getProductId())
          .orElseThrow(() -> new NoSuchElementException("Producto no encontrado con ID: " + item.getProductId()));

      OrderDetail detail = OrderDetail.builder()
          .order(order)
          .product(product)
          .quantity(item.getQuantity())
          .build();

      details.add(detail);

      total = total.add(product.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())));
    }

    order.setTotal(total);
    order.setDetails(details);

    Order savedOrder = orderRepository.save(order);

    return toDTO(savedOrder);
  }

  @Override
  public OrderDTO getOrderById(Long id) {
    Order order = orderRepository.findById(id)
        .orElseThrow(() -> new NoSuchElementException("Orden no encontrada con ID: " + id));
    return toDTO(order);
  }

  @Override
  public List<OrderDTO> getAllOrders() {
    return orderRepository.findAll().stream()
        .map(this::toDTO)
        .collect(Collectors.toList());
  }

  @Override
  public OrderDTO updateOrder(Long id, OrderUpdateDTO orderUpdateDTO) {
    Order order = orderRepository.findById(id)
        .orElseThrow(() -> new NoSuchElementException("Orden no encontrada con ID: " + id));

    if (orderUpdateDTO.getTableId() != null) {
      RestaurantTable table = restaurantTableRepository.findById(orderUpdateDTO.getTableId())
          .orElseThrow(() -> new NoSuchElementException("Mesa no encontrada con ID: " + orderUpdateDTO.getTableId()));
      order.setTable(table);
    }

    if (orderUpdateDTO.getNotes() != null) {
      order.setNotes(orderUpdateDTO.getNotes());
    }

    if (orderUpdateDTO.getStatus() != null) {
      order.setStatus(orderUpdateDTO.getStatus());
    }

    Order updatedOrder = orderRepository.save(order);
    return toDTO(updatedOrder);
  }

  @Override
  public void deleteOrder(Long id) {
    if (!orderRepository.existsById(id)) {
      throw new NoSuchElementException("No existe una orden con ID: " + id);
    }
    orderRepository.deleteById(id);
  }

}
