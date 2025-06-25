package com.Java2.proyecto.diego.AlkemyDiego.model;

import com.Java2.proyecto.diego.AlkemyDiego.enums.Role;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

  @Test
  void testBuilderAndGetters() {
    User user = User.builder()
        .id("1")
        .name("Diego")
        .username("diego@mail.com")
        .password("password123")
        .roles(List.of(Role.USER, Role.ADMIN))
        .build();

    assertEquals("1", user.getId());
    assertEquals("Diego", user.getName());
    assertEquals("diego@mail.com", user.getUsername());
    assertEquals("password123", user.getPassword());
    assertEquals(List.of(Role.USER, Role.ADMIN), user.getRoles());
  }

  @Test
  void testGetAuthorities() {
    User user = User.builder()
        .roles(List.of(Role.USER, Role.ADMIN))
        .build();

    var authorities = user.getAuthorities();
    assertTrue(authorities.contains(new SimpleGrantedAuthority("ROLE_USER")));
    assertTrue(authorities.contains(new SimpleGrantedAuthority("ROLE_ADMIN")));
    assertEquals(2, authorities.size());
  }

  @Test
  void testGetAuthoritiesWhenRolesNull() {
    User user = User.builder().roles(null).build();
    assertTrue(user.getAuthorities().isEmpty());
  }

  @Test
  void testAccountStatusMethods() {
    User user = new User();
    assertTrue(user.isAccountNonExpired());
    assertTrue(user.isAccountNonLocked());
    assertTrue(user.isCredentialsNonExpired());
    assertTrue(user.isEnabled());
  }
}