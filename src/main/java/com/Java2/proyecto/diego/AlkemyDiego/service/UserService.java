package com.Java2.proyecto.diego.AlkemyDiego.service;

import com.Java2.proyecto.diego.AlkemyDiego.dto.UserDto;

import java.util.List;

public interface UserService {
  UserDto createUser(UserDto userDto);
  List<UserDto> getAllUsers();
  UserDto getUser(String id);
  UserDto updateUser(String id, UserDto userDto);
  void deleteUser(String id);
}
