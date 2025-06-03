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

import com.OrderNet.ProyWebIntegrado.dto.user.UserCreateDTO;
import com.OrderNet.ProyWebIntegrado.dto.user.UserDTO;
import com.OrderNet.ProyWebIntegrado.dto.user.UserUpdateDTO;
import com.OrderNet.ProyWebIntegrado.service.user.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;

  @PostMapping("/create")
  public ResponseEntity<UserDTO> createUser(@Valid @RequestBody UserCreateDTO userCreateDTO) {
    UserDTO createdUser = userService.createUser(userCreateDTO);
    return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
  }

  @GetMapping("/{id}")
  public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
    UserDTO user = userService.getUserById(id);
    return ResponseEntity.ok(user);
  }

  @GetMapping
  public ResponseEntity<List<UserDTO>> getAllUsers() {
    List<UserDTO> users = userService.getAllUsers();
    return ResponseEntity.ok(users);
  }

  @PutMapping("/update/{id}")
  public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @Valid @RequestBody UserUpdateDTO userUpdateDTO) {
    UserDTO updatedUser = userService.updateUser(id, userUpdateDTO);
    return ResponseEntity.ok(updatedUser);
  }

  @DeleteMapping("/delete/{id}")
  public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
    userService.deleteUser(id);
    return ResponseEntity.noContent().build();
  }
}
