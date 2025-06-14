package com.Java2.proyecto.diego.AlkemyDiego.security.controller;

import com.Java2.proyecto.diego.AlkemyDiego.security.dto.AuthResponse;
import com.Java2.proyecto.diego.AlkemyDiego.security.dto.LoginRequest;
import com.Java2.proyecto.diego.AlkemyDiego.security.dto.RegisterRequest;
import com.Java2.proyecto.diego.AlkemyDiego.security.service.AuthService;
import com.Java2.proyecto.diego.AlkemyDiego.service.AsyncTaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
  private final AuthService authService;
  private final AsyncTaskService asyncTaskService;

  @PostMapping("/login")
  public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
    try {
      AuthResponse response = authService.login(loginRequest);
      asyncTaskService.logUserLogin(loginRequest.getUsername());
      return ResponseEntity.ok(response);
    } catch (RuntimeException e) {
      return ResponseEntity.status(401).body(e.getMessage());
    }
  }

  @PostMapping("/register")
  public ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest) {
    try {
      AuthResponse response = authService.register(registerRequest);
      asyncTaskService.logUserRegistration(registerRequest.getUsername());
      return ResponseEntity.ok(response);
    } catch (RuntimeException e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }
}
