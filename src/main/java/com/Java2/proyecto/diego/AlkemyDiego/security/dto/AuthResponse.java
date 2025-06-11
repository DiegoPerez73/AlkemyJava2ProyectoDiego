package com.Java2.proyecto.diego.AlkemyDiego.security.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthResponse {
  private String token;
}
