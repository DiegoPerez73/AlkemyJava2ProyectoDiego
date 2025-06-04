package com.Java2.proyecto.diego.AlkemyDiego.service.impl;

import com.Java2.proyecto.diego.AlkemyDiego.dto.UserDto;
import com.Java2.proyecto.diego.AlkemyDiego.enums.Role;
import com.Java2.proyecto.diego.AlkemyDiego.mapper.UserMapper;
import com.Java2.proyecto.diego.AlkemyDiego.repository.UserRepository;
import com.Java2.proyecto.diego.AlkemyDiego.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

  @Autowired
  private UserRepository userRepository;
  @Autowired
  private UserMapper userMapper;

  @Override
  public UserDto createUser(UserDto userDto) {
    var user = userMapper.toEntity(userDto);
    userRepository.save(user);
    return userMapper.toDto(user);
  }

  @Override
  public List<UserDto> getAllUsers() {
    return userRepository.findAll().stream()
        .map(userMapper::toDto)
        .toList();
  }

  @Override
  public UserDto getUser(String id) {
    return userRepository.findById(id)
        .map(userMapper::toDto)
        .orElse(null);
  }

  @Override
  public UserDto updateUser(String id, UserDto userDto) {
    var optionalUser = userRepository.findById(id);
    if (optionalUser.isPresent()) {
      var user = optionalUser.get();
      user.setName(userDto.getName());
      user.setUsername(userDto.getUsername());
      user.setPassword(userDto.getPassword());
      user.setRoles(userDto.getRoles().stream()
          .map(Role::valueOf)
          .collect(java.util.stream.Collectors.toList()));
      userRepository.save(user);
      return userMapper.toDto(user);
    }
    return null;
  }

  @Override
  public void deleteUser(String id) {
    userRepository.deleteById(id);
  }
}
