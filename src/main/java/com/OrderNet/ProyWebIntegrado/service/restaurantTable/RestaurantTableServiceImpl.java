package com.OrderNet.ProyWebIntegrado.service.restaurantTable;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.OrderNet.ProyWebIntegrado.dto.restaurantTable.RestaurantTableCreateDTO;
import com.OrderNet.ProyWebIntegrado.dto.restaurantTable.RestaurantTableDTO;
import com.OrderNet.ProyWebIntegrado.dto.restaurantTable.RestaurantTableUpdateDTO;
import com.OrderNet.ProyWebIntegrado.dto.user.UserShortDTO;
import com.OrderNet.ProyWebIntegrado.persistence.model.entities.RestaurantTable;
import com.OrderNet.ProyWebIntegrado.persistence.model.entities.User;
import com.OrderNet.ProyWebIntegrado.persistence.model.enums.Role;
import com.OrderNet.ProyWebIntegrado.persistence.repository.RestaurantTableRepository;
import com.OrderNet.ProyWebIntegrado.persistence.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class RestaurantTableServiceImpl implements RestaurantTableService {

  private final RestaurantTableRepository restaurantTableRepository;
  private final UserRepository userRepository;

  private UserShortDTO toUserDTO(User user) {
    return UserShortDTO.builder()
        .id(user.getId())
        .name(user.getName())
        .email(user.getEmail())
        .build();
  }

  private RestaurantTableDTO toDTO(RestaurantTable restaurantTable) {
    User waiter = restaurantTable.getWaiter();
    return RestaurantTableDTO.builder()
        .id(restaurantTable.getId())
        .code(restaurantTable.getCode())
        .status(restaurantTable.getStatus())
        .userShortDTO(waiter != null ? toUserDTO(waiter) : null)
        .build();
  }

  @Override
  public RestaurantTableDTO createRestaurantTable(RestaurantTableCreateDTO restaurantTableCreateDTO) {
    RestaurantTable newRestaurantTable = RestaurantTable.builder()
        .code(restaurantTableCreateDTO.getCode())
        .status(restaurantTableCreateDTO.getStatus())
        .build();

    RestaurantTable savedRestaurantTable = restaurantTableRepository.save(newRestaurantTable);
    return toDTO(savedRestaurantTable);
  }

  @Override
  public RestaurantTableDTO getRestaurantTableById(Long id) {
    RestaurantTable restaurantTable = restaurantTableRepository.findById(id)
        .orElseThrow(() -> new NoSuchElementException("Mesa no encontrada con ID: " + id));
    return toDTO(restaurantTable);
  }

  @Override
  public List<RestaurantTableDTO> getAllRestaurantTables() {
    return restaurantTableRepository.findAll().stream()
        .map(this::toDTO)
        .collect(Collectors.toList());
  }

  @Override
  public RestaurantTableDTO updateRestaurantTable(Long id, RestaurantTableUpdateDTO restaurantTableUpdateDTO) {
    RestaurantTable restaurantTable = restaurantTableRepository.findById(id)
        .orElseThrow(() -> new NoSuchElementException("Mesa no encontrada con ID: " + id));

    if (restaurantTableUpdateDTO.getCode() != null) {
      restaurantTable.setCode(restaurantTableUpdateDTO.getCode());
    }

    if (restaurantTableUpdateDTO.getStatus() != null) {
      restaurantTable.setStatus(restaurantTableUpdateDTO.getStatus());
    }

    if (restaurantTableUpdateDTO.getWaiterId() != null) {
      Long waiterId = restaurantTableUpdateDTO.getWaiterId();

      if (waiterId == -1) {
        restaurantTable.setWaiter(null);
      } else {
        User user = userRepository.findById(waiterId)
            .orElseThrow(() -> new NoSuchElementException("Mozo no encontrado con ID: " + waiterId));

        if (user.getRole() != Role.WAITER) {
          throw new IllegalArgumentException("El usuario no es un mozo");
        }

        restaurantTable.setWaiter(user);
      }
    }

    RestaurantTable updatedRestaurantTable = restaurantTableRepository.save(restaurantTable);
    return toDTO(updatedRestaurantTable);
  }

  @Override
  public void deleteRestaurantTable(Long id) {
    if (!restaurantTableRepository.existsById(id)) {
      throw new NoSuchElementException("No existe una mesa con ID: " + id);
    }
    restaurantTableRepository.deleteById(id);
  }

}
