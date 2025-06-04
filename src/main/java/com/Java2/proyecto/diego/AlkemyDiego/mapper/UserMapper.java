package com.Java2.proyecto.diego.AlkemyDiego.mapper;

import com.Java2.proyecto.diego.AlkemyDiego.dto.UserDto;
import com.Java2.proyecto.diego.AlkemyDiego.enums.Role;
import com.Java2.proyecto.diego.AlkemyDiego.model.User;
import org.mapstruct.Mapper;

import java.util.stream.Collectors;

@Mapper(componentModel = "Spring")
public interface UserMapper {
  default UserDto toDto(User user) {
    if (user == null) {
      return null;
    }
    return UserDto.builder()
        .id(user.getId())
        .name(user.getName())
        .username(user.getUsername())
        .password(user.getPassword())
        .roles(user.getRoles().stream().map(Role::getRole).collect(Collectors.toList()))
        .build();
  }

  default User toEntity(UserDto userDto) {
    if (userDto == null) {
      return null;
    }
    return User.builder()
        .id(userDto.getId())
        .name(userDto.getName())
        .username(userDto.getUsername())
        .password(userDto.getPassword())
        .roles(userDto.getRoles().stream().map(Role::valueOf).collect(Collectors.toList()))
        .build();
  }
}
