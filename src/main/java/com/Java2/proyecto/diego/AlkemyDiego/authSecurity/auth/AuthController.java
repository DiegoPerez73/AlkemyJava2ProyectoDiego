package com.Java2.proyecto.diego.AlkemyDiego.authSecurity.auth;

import com.Java2.proyecto.diego.AlkemyDiego.authSecurity.dto.AuthRequest;
import com.Java2.proyecto.diego.AlkemyDiego.authSecurity.dto.AuthResponse;
import com.Java2.proyecto.diego.AlkemyDiego.authSecurity.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

  private final AuthService authService;

  @PostMapping("/login")
  public ResponseEntity<AuthResponse> login(
      @Valid @RequestBody AuthRequest request
  ) {
    return ResponseEntity.ok(authService.authenticate(request));
  }
}
