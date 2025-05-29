package com.Java2.proyecto.diego.AlkemyDiego.authSecurity.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class AuthResponse {
  private String token;
}
