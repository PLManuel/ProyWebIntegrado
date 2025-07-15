package com.OrderNet.ProyWebIntegrado.service.order;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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

    if (Boolean.FALSE.equals(waiter.getSessionActive())) {
      throw new IllegalStateException("El mozo no tiene una sesión activa");
    }

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

    if (orderUpdateDTO.getWaiterId() != null) {
      User newWaiter = userRepository.findById(orderUpdateDTO.getWaiterId())
          .orElseThrow(() -> new NoSuchElementException("Mozo no encontrado con ID: " + orderUpdateDTO.getWaiterId()));

      if (!newWaiter.getSessionActive()) {
        throw new IllegalStateException("El mozo no tiene una sesión activa");
      }

      order.setWaiter(newWaiter);
    }

    if (orderUpdateDTO.getDetails() != null) {
      order.getDetails().size();
      order.getDetails().forEach(detail -> detail.setOrder(null));
      order.getDetails().clear();
      List<OrderDetail> newDetails = new ArrayList<>();
      BigDecimal newTotal = BigDecimal.ZERO;
      for (OrderDetailCreateDTO detailDTO : orderUpdateDTO.getDetails()) {
        Product product = productRepository.findById(detailDTO.getProductId())
            .orElseThrow(
                () -> new NoSuchElementException("Producto no encontrado con ID: " + detailDTO.getProductId()));

        OrderDetail newDetail = OrderDetail.builder()
            .product(product)
            .quantity(detailDTO.getQuantity())
            .build();

        newDetail.setOrder(order);
        newDetails.add(newDetail);
        newTotal = newTotal.add(product.getPrice().multiply(BigDecimal.valueOf(detailDTO.getQuantity())));
      }
      order.getDetails().addAll(newDetails);
      order.setTotal(newTotal);
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

  @Override
  public ByteArrayInputStream generateOrdersExcel(LocalDate startDate, LocalDate endDate) throws IOException {
    List<Order> orders = orderRepository.findAll().stream()
        .filter(order -> {
          LocalDate orderDate = order.getCreatedAt().toLocalDate();
          return !orderDate.isBefore(startDate) && !orderDate.isAfter(endDate);
        })
        .toList();

    try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
      Sheet sheet = workbook.createSheet("Órdenes");

      Row header = sheet.createRow(0);
      String[] columns = { "ID", "Fecha", "Estado", "Mesa", "Mozo", "Total", "Productos" };
      for (int i = 0; i < columns.length; i++) {
        header.createCell(i).setCellValue(columns[i]);
      }

      int rowNum = 1;
      for (Order order : orders) {
        for (OrderDetail detail : order.getDetails()) {
          Row row = sheet.createRow(rowNum++);
          row.createCell(0).setCellValue(order.getId());
          row.createCell(1).setCellValue(order.getCreatedAt().toString());
          row.createCell(2).setCellValue(order.getStatus().toString());
          row.createCell(3).setCellValue(order.getTable().getCode());
          row.createCell(4).setCellValue(order.getWaiter().getName());
          row.createCell(5).setCellValue(order.getTotal().doubleValue());
          row.createCell(6).setCellValue(detail.getProduct().getName() + " x" + detail.getQuantity());
        }
      }

      workbook.write(out);
      return new ByteArrayInputStream(out.toByteArray());
    }
  }
}
