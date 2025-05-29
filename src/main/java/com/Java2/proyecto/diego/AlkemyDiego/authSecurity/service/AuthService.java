package com.Java2.proyecto.diego.AlkemyDiego.authSecurity.service;

import com.Java2.proyecto.diego.AlkemyDiego.authSecurity.dto.AuthRequest;
import com.Java2.proyecto.diego.AlkemyDiego.authSecurity.dto.AuthResponse;

public interface AuthService {
  AuthResponse authenticate(AuthRequest request);
}
