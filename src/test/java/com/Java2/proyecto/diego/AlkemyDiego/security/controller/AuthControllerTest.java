
package com.Java2.proyecto.diego.AlkemyDiego.security.controller;

import com.Java2.proyecto.diego.AlkemyDiego.security.dto.AuthResponse;
import com.Java2.proyecto.diego.AlkemyDiego.security.dto.LoginRequest;
import com.Java2.proyecto.diego.AlkemyDiego.security.dto.RegisterRequest;
import com.Java2.proyecto.diego.AlkemyDiego.security.service.AuthService;
import com.Java2.proyecto.diego.AlkemyDiego.service.AsyncTaskService;
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

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

@WebMvcTest(AuthController.class)
@Import(AuthControllerTest.MockConfig.class)
class AuthControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private AuthService authService;

  @Autowired
  private AsyncTaskService asyncTaskService;

  @TestConfiguration
  static class MockConfig {
    @Bean
    public AuthService authService() {
      return Mockito.mock(AuthService.class);
    }

    @Bean
    public AsyncTaskService asyncTaskService() {
      return Mockito.mock(AsyncTaskService.class);
    }
  }

  @Test
  @WithMockUser
  void login_returnsOk() throws Exception {
    AuthResponse response = AuthResponse.builder().token("abc123").build();
    Mockito.when(authService.login(any(LoginRequest.class))).thenReturn(response);

    mockMvc.perform(post("/auth/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"username\":\"user\",\"password\":\"pass\"}")
            .with(csrf()))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.token").value("abc123"));
  }

  @Test
  @WithMockUser
  void login_returnsUnauthorized() throws Exception {
    Mockito.when(authService.login(any(LoginRequest.class))).thenThrow(new RuntimeException("Bad credentials"));

    mockMvc.perform(post("/auth/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"username\":\"user\",\"password\":\"wrong\"}")
            .with(csrf()))
        .andExpect(status().isUnauthorized())
        .andExpect(content().string("Bad credentials"));
  }

  @Test
  @WithMockUser
  void register_returnsOk() throws Exception {
    AuthResponse response = AuthResponse.builder().token("regtoken").build();
    Mockito.when(authService.register(any(RegisterRequest.class))).thenReturn(response);

    mockMvc.perform(post("/auth/register")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"name\":\"Diego\",\"username\":\"user\",\"password\":\"pass\",\"roles\":[\"USER\"]}")
            .with(csrf()))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.token").value("regtoken"));
  }

  @Test
  @WithMockUser
  void register_returnsBadRequest() throws Exception {
    Mockito.when(authService.register(any(RegisterRequest.class))).thenThrow(new RuntimeException("User exists"));

    mockMvc.perform(post("/auth/register")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"name\":\"Diego\",\"username\":\"user\",\"password\":\"pass\",\"roles\":[\"USER\"]}")
            .with(csrf()))
        .andExpect(status().isBadRequest())
        .andExpect(content().string("User exists"));
  }
}