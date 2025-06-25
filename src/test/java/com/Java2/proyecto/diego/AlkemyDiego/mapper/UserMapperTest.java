package com.Java2.proyecto.diego.AlkemyDiego.mapper;

import com.Java2.proyecto.diego.AlkemyDiego.dto.UserDto;
import com.Java2.proyecto.diego.AlkemyDiego.enums.Role;
import com.Java2.proyecto.diego.AlkemyDiego.model.User;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserMapperTest {

  private final UserMapper userMapper = new UserMapper() {
  };

  @Test
  void testToDto() {
    User user = User.builder()
        .id("1")
        .name("Diego")
        .username("dperez")
        .password("1234")
        .roles(List.of(Role.ADMIN, Role.USER))
        .build();

    UserDto dto = userMapper.toDto(user);

    assertNotNull(dto);
    assertEquals("1", dto.getId());
    assertEquals("Diego", dto.getName());
    assertEquals("dperez", dto.getUsername());
    assertEquals("1234", dto.getPassword());
    assertEquals(List.of("ADMIN", "USER"), dto.getRoles());
  }

  @Test
  void testToEntity() {
    UserDto dto = UserDto.builder()
        .id("2")
        .name("Ana")
        .username("ana")
        .password("abcd")
        .roles(List.of("USER"))
        .build();

    User user = userMapper.toEntity(dto);

    assertNotNull(user);
    assertEquals("2", user.getId());
    assertEquals("Ana", user.getName());
    assertEquals("ana", user.getUsername());
    assertEquals("abcd", user.getPassword());
    assertEquals(List.of(Role.USER), user.getRoles());
  }
}