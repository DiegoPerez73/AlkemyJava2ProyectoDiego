package com.Java2.proyecto.diego.AlkemyDiego.service.impl;

import com.Java2.proyecto.diego.AlkemyDiego.dto.UserDto;
import com.Java2.proyecto.diego.AlkemyDiego.enums.Role;
import com.Java2.proyecto.diego.AlkemyDiego.mapper.UserMapper;
import com.Java2.proyecto.diego.AlkemyDiego.model.User;
import com.Java2.proyecto.diego.AlkemyDiego.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

  @Mock
  private UserRepository userRepository;
  @Mock
  private UserMapper userMapper;
  @InjectMocks
  private UserServiceImpl userService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testCreateUser() {
    UserDto userDto = UserDto.builder()
        .id("1")
        .name("Diego")
        .username("diego@mail.com")
        .password("password123")
        .roles(List.of("USER"))
        .build();

    User user = User.builder()
        .id("1")
        .name("Diego")
        .username("diego@mail.com")
        .password("password123")
        .roles(List.of(Role.USER))
        .build();

    when(userMapper.toEntity(userDto)).thenReturn(user);
    when(userMapper.toDto(user)).thenReturn(userDto);

    UserDto result = userService.createUser(userDto);

    verify(userRepository).save(user);
    assertEquals(userDto, result);
  }

  @Test
  void testGetAllUsers() {
      User user = User.builder()
          .id("1")
          .name("Diego")
          .username("diego@mail.com")
          .password("password123")
          .roles(List.of(Role.USER))
          .build();
      UserDto userDto = UserDto.builder()
          .id("1")
          .name("Diego")
          .username("diego@mail.com")
          .password("password123")
          .roles(List.of("USER"))
          .build();

      when(userRepository.findAll()).thenReturn(List.of(user));
      when(userMapper.toDto(user)).thenReturn(userDto);

      List<UserDto> result = userService.getAllUsers();

      assertEquals(1, result.size());
      assertEquals(userDto, result.get(0));
  }

  @Test
  void testGetUserFound() {
      User user = User.builder().id("1").build();
      UserDto userDto = UserDto.builder().id("1").build();

      when(userRepository.findById("1")).thenReturn(java.util.Optional.of(user));
      when(userMapper.toDto(user)).thenReturn(userDto);

      UserDto result = userService.getUser("1");

      assertEquals(userDto, result);
  }

  @Test
  void testGetUserNotFound() {
      when(userRepository.findById("2")).thenReturn(java.util.Optional.empty());

      UserDto result = userService.getUser("2");

      assertNull(result);
  }

  @Test
  void testUpdateUserFound() {
      User user = User.builder()
          .id("1")
          .name("Old")
          .username("old@mail.com")
          .password("oldpass")
          .roles(List.of(Role.USER))
          .build();
      UserDto userDto = UserDto.builder()
          .id("1")
          .name("New")
          .username("new@mail.com")
          .password("newpass")
          .roles(List.of("ADMIN"))
          .build();
      User updatedUser = User.builder()
          .id("1")
          .name("New")
          .username("new@mail.com")
          .password("newpass")
          .roles(List.of(Role.ADMIN))
          .build();
      UserDto updatedDto = UserDto.builder()
          .id("1")
          .name("New")
          .username("new@mail.com")
          .password("newpass")
          .roles(List.of("ADMIN"))
          .build();

      when(userRepository.findById("1")).thenReturn(java.util.Optional.of(user));
      when(userMapper.toDto(user)).thenReturn(updatedDto);

      UserDto result = userService.updateUser("1", userDto);

      verify(userRepository).save(user);
      assertEquals(updatedDto, result);
  }

  @Test
  void testUpdateUserNotFound() {
      UserDto userDto = UserDto.builder().id("2").build();
      when(userRepository.findById("2")).thenReturn(java.util.Optional.empty());

      UserDto result = userService.updateUser("2", userDto);

      assertNull(result);
  }

  @Test
  void testDeleteUser() {
      userService.deleteUser("1");
      verify(userRepository).deleteById("1");
  }
}
