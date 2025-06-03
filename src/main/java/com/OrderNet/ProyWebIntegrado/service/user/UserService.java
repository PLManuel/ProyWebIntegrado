package com.OrderNet.ProyWebIntegrado.service.user;

import java.util.List;

import org.springframework.stereotype.Service;

import com.OrderNet.ProyWebIntegrado.dto.user.UserCreateDTO;
import com.OrderNet.ProyWebIntegrado.dto.user.UserDTO;
import com.OrderNet.ProyWebIntegrado.dto.user.UserUpdateDTO;

@Service
public interface UserService {
  UserDTO createUser(UserCreateDTO userCreateDTO);

  UserDTO getUserById(Long id);

  List<UserDTO> getAllUsers();

  UserDTO updateUser(Long id, UserUpdateDTO userUpdateDTO);

  void deleteUser(Long id);
}
