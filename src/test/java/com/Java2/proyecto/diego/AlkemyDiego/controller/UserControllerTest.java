
package com.Java2.proyecto.diego.AlkemyDiego.controller;

import com.Java2.proyecto.diego.AlkemyDiego.dto.UserDto;
import com.Java2.proyecto.diego.AlkemyDiego.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

@WebMvcTest(UserController.class)
@Import(UserControllerTest.MockConfig.class)
class UserControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private UserService userService;

  @TestConfiguration
  static class MockConfig {
    @Bean
    public UserService userService() {
      return Mockito.mock(UserService.class);
    }
  }

  @Test
  @WithMockUser
  void findAll_returnsListOfUsers() throws Exception {
    UserDto user = new UserDto();
    Mockito.when(userService.getAllUsers()).thenReturn(Collections.singletonList(user));

    mockMvc.perform(get("/users")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$").isArray());
  }

  @Test
  @WithMockUser
  void getUser_returnsUser() throws Exception {
    UserDto user = new UserDto();
    Mockito.when(userService.getUser("1")).thenReturn(user);

    mockMvc.perform(get("/users/1")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
  }

  @Test
  @WithMockUser(roles = "ADMIN")
  void createUser_returnsCreatedUser() throws Exception {
    UserDto user = new UserDto();
    Mockito.when(userService.createUser(any(UserDto.class))).thenReturn(user);

    mockMvc.perform(post("/users")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{}")
            .with(csrf()))
        .andExpect(status().isOk());
  }

  @Test
  @WithMockUser(roles = "ADMIN")
  void updateUser_returnsUpdatedUser() throws Exception {
    UserDto user = new UserDto();
    Mockito.when(userService.updateUser(eq("1"), any(UserDto.class))).thenReturn(user);

    mockMvc.perform(put("/users/1")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{}")
            .with(csrf()))
        .andExpect(status().isOk());
  }

  @Test
  @WithMockUser(roles = "ADMIN")
  void updateUser_returnsNotFound() throws Exception {
    Mockito.when(userService.updateUser(eq("1"), any(UserDto.class))).thenReturn(null);

    mockMvc.perform(put("/users/1")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{}")
            .with(csrf()))
        .andExpect(status().isNotFound());
  }

  @Test
  @WithMockUser(roles = "ADMIN")
  void deleteUser_returnsNoContent() throws Exception {
    mockMvc.perform(delete("/users/1")
            .with(csrf()))
        .andExpect(status().isNoContent());
  }
}