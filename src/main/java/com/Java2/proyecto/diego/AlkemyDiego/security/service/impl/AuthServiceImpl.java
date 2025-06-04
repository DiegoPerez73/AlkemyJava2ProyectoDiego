package com.Java2.proyecto.diego.AlkemyDiego.security.service.impl;

import com.Java2.proyecto.diego.AlkemyDiego.security.dto.AuthResponse;
import com.Java2.proyecto.diego.AlkemyDiego.security.dto.LoginRequest;
import com.Java2.proyecto.diego.AlkemyDiego.security.service.AuthService;
import com.Java2.proyecto.diego.AlkemyDiego.security.service.JwtService;
import com.Java2.proyecto.diego.AlkemyDiego.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
  private final UserService userService;
  private final JwtService jwtService;

  @Override
  public AuthResponse login(LoginRequest loginRequest) {
    var user = userService.getAllUsers().stream()
        .filter(u -> u.getUsername().equals(loginRequest.getUsername()) &&
            u.getPassword().equals(loginRequest.getPassword()))
        .findFirst()
        .orElseThrow(() -> new RuntimeException("Credenciales inválidas"));

    String token = jwtService.generateToken(user.getUsername(), user.getRoles());
    return AuthResponse.builder().token(token).build();
  }
}
