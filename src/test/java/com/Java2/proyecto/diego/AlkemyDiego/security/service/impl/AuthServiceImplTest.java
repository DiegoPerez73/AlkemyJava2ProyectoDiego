package com.Java2.proyecto.diego.AlkemyDiego.security.service.impl;

import com.Java2.proyecto.diego.AlkemyDiego.dto.UserDto;
import com.Java2.proyecto.diego.AlkemyDiego.security.dto.AuthResponse;
import com.Java2.proyecto.diego.AlkemyDiego.security.dto.LoginRequest;
import com.Java2.proyecto.diego.AlkemyDiego.security.dto.RegisterRequest;
import com.Java2.proyecto.diego.AlkemyDiego.security.service.JwtService;
import com.Java2.proyecto.diego.AlkemyDiego.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthServiceImplTest {

    @Mock
    private UserService userService;
    @Mock
    private JwtService jwtService;
    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthServiceImpl authService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void login_successful_returnsToken() {
        String username = "testuser";
        String password = "password";
        String encodedPassword = "encoded";
        List<String> roles = List.of("USER");
        String token = "jwt-token";

        UserDto userDto = UserDto.builder()
                .username(username)
                .password(encodedPassword)
                .roles(roles)
                .build();

        when(userService.getAllUsers()).thenReturn(List.of(userDto));
        when(passwordEncoder.matches(password, encodedPassword)).thenReturn(true);
        when(jwtService.generateToken(username, roles)).thenReturn(token);

        LoginRequest loginRequest = LoginRequest.builder()
                .username(username)
                .password(password)
                .build();

        AuthResponse response = authService.login(loginRequest);

        assertNotNull(response);
        assertEquals(token, response.getToken());
    }

    @Test
    void login_invalidCredentials_throwsException() {
        String username = "testuser";
        String password = "wrong";
        String encodedPassword = "encoded";

        UserDto userDto = UserDto.builder()
                .username(username)
                .password(encodedPassword)
                .roles(List.of("USER"))
                .build();

        when(userService.getAllUsers()).thenReturn(List.of(userDto));
        when(passwordEncoder.matches(password, encodedPassword)).thenReturn(false);

        LoginRequest loginRequest = LoginRequest.builder()
                .username(username)
                .password(password)
                .build();

        assertThrows(RuntimeException.class, () -> authService.login(loginRequest));
    }

    @Test
    void login_userNotFound_throwsException() {
        when(userService.getAllUsers()).thenReturn(List.of());

        LoginRequest loginRequest = LoginRequest.builder()
                .username("noexiste")
                .password("pass")
                .build();

        assertThrows(RuntimeException.class, () -> authService.login(loginRequest));
    }

    @Test
    void register_successful_returnsToken() {
        String username = "nuevo";
        String password = "pass";
        String encodedPassword = "encoded";
        List<String> roles = List.of("USER");
        String token = "jwt-token";

        RegisterRequest registerRequest = RegisterRequest.builder()
                .name("Nuevo")
                .username(username)
                .password(password)
                .roles(roles)
                .build();

        UserDto userDto = UserDto.builder()
                .name("Nuevo")
                .username(username)
                .password(encodedPassword)
                .roles(roles)
                .build();

        when(passwordEncoder.encode(password)).thenReturn(encodedPassword);
        when(jwtService.generateToken(username, roles)).thenReturn(token);

        AuthResponse response = authService.register(registerRequest);

        verify(userService).createUser(argThat(u ->
                u.getUsername().equals(username) &&
                u.getPassword().equals(encodedPassword) &&
                u.getRoles().equals(roles)
        ));
        assertNotNull(response);
        assertEquals(token, response.getToken());
    }
}