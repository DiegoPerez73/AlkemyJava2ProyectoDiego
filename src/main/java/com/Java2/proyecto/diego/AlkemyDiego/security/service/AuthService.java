package com.Java2.proyecto.diego.AlkemyDiego.security.service;

import com.Java2.proyecto.diego.AlkemyDiego.security.dto.AuthResponse;
import com.Java2.proyecto.diego.AlkemyDiego.security.dto.LoginRequest;

public interface AuthService {
  AuthResponse login(LoginRequest loginRequest);
}
