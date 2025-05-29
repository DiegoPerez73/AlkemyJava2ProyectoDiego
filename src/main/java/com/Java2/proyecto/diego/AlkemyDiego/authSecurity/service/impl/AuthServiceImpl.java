package com.Java2.proyecto.diego.AlkemyDiego.authSecurity.service.impl;

  import com.Java2.proyecto.diego.AlkemyDiego.authSecurity.dto.AuthRequest;
  import com.Java2.proyecto.diego.AlkemyDiego.authSecurity.dto.AuthResponse;
  import com.Java2.proyecto.diego.AlkemyDiego.authSecurity.service.AuthService;
  import com.Java2.proyecto.diego.AlkemyDiego.authSecurity.service.JwtService;
  import com.Java2.proyecto.diego.AlkemyDiego.enums.Role;
  import com.google.cloud.firestore.Firestore;
  import com.google.cloud.firestore.QueryDocumentSnapshot;
  import lombok.AllArgsConstructor;
  import lombok.RequiredArgsConstructor;
  import lombok.extern.slf4j.Slf4j;
  import org.springframework.security.authentication.AuthenticationManager;
  import org.springframework.security.authentication.BadCredentialsException;
  import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
  import org.springframework.security.core.AuthenticationException;
  import org.springframework.security.core.userdetails.User;
  import org.springframework.security.core.userdetails.UsernameNotFoundException;
  import org.springframework.security.crypto.password.PasswordEncoder;
  import org.springframework.stereotype.Service;

  import java.util.List;
  import java.util.Set;

@Service
  @RequiredArgsConstructor
  @Slf4j
  public class AuthServiceImpl implements AuthService {
    private final Firestore firestore;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;


    @Override
    public AuthResponse authenticate(AuthRequest request) {
      log.debug("Autenticando usuario: {}", request.getUsername());

      try {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        User user = findUserByUsername(request.getUsername());
        if (user == null) {
          log.error("Usuario no encontrado después de autenticación exitosa: {}", request.getUsername());
          throw new UsernameNotFoundException("User not found");
        }

        String jwtToken = jwtService.generateToken(user);
        log.info("Usuario {} autenticado exitosamente", user.getUsername());
        return AuthResponse.builder()
            .token(jwtToken)
            .build();

      } catch (AuthenticationException e) {
        log.warn("Falló autenticación para usuario: {}", request.getUsername());
        throw new BadCredentialsException("Invalid credentials");
      }
    }

  private User findUserByUsername(String username) {
    try {
      List<QueryDocumentSnapshot> documents = firestore.collection("users")
          .whereEqualTo("username", username)
          .get()
          .get()
          .getDocuments();

      if (documents.isEmpty()) {
        return null;
      }

      return documents.get(0).toObject(User.class);
    } catch (Exception e) {
      log.error("Error al buscar usuario en Firestore", e);
      return null;
    }
  }
  }