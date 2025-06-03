package com.OrderNet.ProyWebIntegrado.service.user;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.OrderNet.ProyWebIntegrado.dto.user.UserCreateDTO;
import com.OrderNet.ProyWebIntegrado.dto.user.UserDTO;
import com.OrderNet.ProyWebIntegrado.dto.user.UserUpdateDTO;
import com.OrderNet.ProyWebIntegrado.persistence.model.entities.User;
import com.OrderNet.ProyWebIntegrado.persistence.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  private UserDTO toDTO(User user) {
    return UserDTO.builder()
        .id(user.getId())
        .name(user.getName())
        .email(user.getEmail())
        .role(user.getRole())
        .active(user.getActive())
        .build();
  }

  @Override
  public UserDTO createUser(UserCreateDTO userCreateDTO) {
    userRepository.findByEmail(userCreateDTO.getEmail())
        .ifPresent(_ -> {
          throw new IllegalArgumentException("El correo ya está registrado");
        });

    User newUser = User.builder()
        .name(userCreateDTO.getName())
        .email(userCreateDTO.getEmail())
        .password(passwordEncoder.encode(userCreateDTO.getPassword()))
        .role(userCreateDTO.getRole())
        .active(userCreateDTO.getActive() != null ? userCreateDTO.getActive() : true)
        .build();

    User savedUser = userRepository.save(newUser);
    return toDTO(savedUser);
  }

  @Override
  public UserDTO getUserById(Long id) {
    User user = userRepository.findById(id)
        .orElseThrow(() -> new NoSuchElementException("Usuario no encontrado con ID: " + id));
    return toDTO(user);
  }

  @Override
  public List<UserDTO> getAllUsers() {
    return userRepository.findAll().stream()
        .map(this::toDTO)
        .collect(Collectors.toList());
  }

  @Override
  public UserDTO updateUser(Long id, UserUpdateDTO userUpdateDTO) {
    User user = userRepository.findById(id)
        .orElseThrow(() -> new NoSuchElementException("Usuario no encontrado con ID: " + id));

    if (userUpdateDTO.getName() != null) {
      user.setName(userUpdateDTO.getName());
    }

    if (userUpdateDTO.getEmail() != null) {
      user.setEmail(userUpdateDTO.getEmail());
    }

    if (userUpdateDTO.getPassword() != null) {
      user.setPassword(passwordEncoder.encode(userUpdateDTO.getPassword()));
    }

    if (userUpdateDTO.getRole() != null) {
      user.setRole(userUpdateDTO.getRole());
    }

    if (userUpdateDTO.getActive() != null) {
      user.setActive(userUpdateDTO.getActive());
    }

    User updatedUser = userRepository.save(user);
    return toDTO(updatedUser);
  }

  @Override
  public void deleteUser(Long id) {
    if (!userRepository.existsById(id)) {
      throw new NoSuchElementException("No existe un usuario con ID: " + id);
    }
    userRepository.deleteById(id);
  }

}
