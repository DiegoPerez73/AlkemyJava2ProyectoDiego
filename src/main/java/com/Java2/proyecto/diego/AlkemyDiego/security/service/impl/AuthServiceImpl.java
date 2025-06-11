package com.Java2.proyecto.diego.AlkemyDiego.security.service.impl;

import com.Java2.proyecto.diego.AlkemyDiego.dto.UserDto;
import com.Java2.proyecto.diego.AlkemyDiego.security.dto.AuthResponse;
import com.Java2.proyecto.diego.AlkemyDiego.security.dto.LoginRequest;
import com.Java2.proyecto.diego.AlkemyDiego.security.dto.RegisterRequest;
import com.Java2.proyecto.diego.AlkemyDiego.security.service.AuthService;
import com.Java2.proyecto.diego.AlkemyDiego.security.service.JwtService;
import com.Java2.proyecto.diego.AlkemyDiego.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
  private final UserService userService;
  private final JwtService jwtService;
  private final PasswordEncoder passwordEncoder;

  @Override
  public AuthResponse login(LoginRequest loginRequest) {
    var user = userService.getAllUsers().stream()
        .filter(u -> u.getUsername().equals(loginRequest.getUsername()))
        .findFirst()
        .orElseThrow(() -> new RuntimeException("Credenciales inválidas"));

    if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
      throw new RuntimeException("Credenciales inválidas");
    }

    String token = jwtService.generateToken(user.getUsername(), user.getRoles());
    return AuthResponse.builder().token(token).build();
  }

@Override
public AuthResponse register(RegisterRequest registerRequest) {
    var userDto = UserDto.builder()
        .name(registerRequest.getName())
        .username(registerRequest.getUsername())
        .password(passwordEncoder.encode(registerRequest.getPassword()))
        .roles(registerRequest.getRoles())
        .build();
    userService.createUser(userDto);
    String token = jwtService.generateToken(userDto.getUsername(), userDto.getRoles());
    return AuthResponse.builder().token(token).build();
}
}
