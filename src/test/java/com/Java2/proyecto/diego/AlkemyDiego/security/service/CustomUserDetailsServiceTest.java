package com.Java2.proyecto.diego.AlkemyDiego.security.service;

import com.Java2.proyecto.diego.AlkemyDiego.dto.UserDto;
import com.Java2.proyecto.diego.AlkemyDiego.mapper.UserMapper;
import com.Java2.proyecto.diego.AlkemyDiego.model.User;
import com.Java2.proyecto.diego.AlkemyDiego.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CustomUserDetailsServiceTest {

    @Mock
    private UserService userService;
    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private CustomUserDetailsService customUserDetailsService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void loadUserByUsername_userExists_returnsUserDetails() {
        String username = "testuser";
        UserDto userDto = UserDto.builder().id("1").username(username).build();
        User user = User.builder().id("1").username(username).build();

        when(userService.getAllUsers()).thenReturn(List.of(userDto));
        when(userMapper.toEntity(userDto)).thenReturn(user);

        var result = customUserDetailsService.loadUserByUsername(username);

        assertNotNull(result);
        assertEquals(username, result.getUsername());
    }

    @Test
    void loadUserByUsername_userNotFound_throwsException() {
        when(userService.getAllUsers()).thenReturn(List.of());

        assertThrows(UsernameNotFoundException.class, () ->
                customUserDetailsService.loadUserByUsername("noexiste"));
    }
}