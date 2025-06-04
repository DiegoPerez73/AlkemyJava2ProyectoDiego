package com.Java2.proyecto.diego.AlkemyDiego.security.service;

import com.Java2.proyecto.diego.AlkemyDiego.mapper.UserMapper;
import com.Java2.proyecto.diego.AlkemyDiego.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
  private final UserService userService;
  private final UserMapper userMapper;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    var userDto = userService.getAllUsers().stream()
        .filter(u -> u.getUsername().equals(username))
        .findFirst()
        .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));
    return userMapper.toEntity(userDto);
  }
}
