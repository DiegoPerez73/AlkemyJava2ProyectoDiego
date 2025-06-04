package com.Java2.proyecto.diego.AlkemyDiego.controller;

import com.Java2.proyecto.diego.AlkemyDiego.dto.UserDto;
import com.Java2.proyecto.diego.AlkemyDiego.model.Product;
import com.Java2.proyecto.diego.AlkemyDiego.model.User;
import com.Java2.proyecto.diego.AlkemyDiego.service.impl.UserServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {
  private final UserServiceImpl userServiceImpl;

  @GetMapping
  public ResponseEntity<List<UserDto>> findAll() {
    List<UserDto> users = userServiceImpl.getAllUsers();
    return ResponseEntity.ok(users);
  }

  @GetMapping("/{id}")
  public ResponseEntity<UserDto> getUser(@PathVariable("id") String id) {
    UserDto userdto = userServiceImpl.getUser(id);
    return ResponseEntity.ok(userdto);
  }

  @PostMapping
  public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto) {
    UserDto created = userServiceImpl.createUser(userDto);
    return ResponseEntity.ok(created);
  }

  @PutMapping("/{id}")
  public ResponseEntity<UserDto> updateUser(@PathVariable("id") String id, @RequestBody UserDto userDto) {
    UserDto updated = userServiceImpl.updateUser(id, userDto);
    if (updated == null) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(updated);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteUser(@PathVariable("id") String id) {
    userServiceImpl.deleteUser(id);
    return ResponseEntity.noContent().build();
  }
}
