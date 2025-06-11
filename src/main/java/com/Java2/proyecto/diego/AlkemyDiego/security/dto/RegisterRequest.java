package com.Java2.proyecto.diego.AlkemyDiego.security.dto;

import lombok.Data;

import java.util.List;

@Data
public class RegisterRequest {
  private String name;
  private String username;
  private String password;
  private List<String> roles; // Ej: ["USER"], ["ADMIN"], etc.
}