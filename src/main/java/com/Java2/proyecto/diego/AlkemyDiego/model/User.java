package com.Java2.proyecto.diego.AlkemyDiego.model;

import com.Java2.proyecto.diego.AlkemyDiego.enums.Role;
import com.google.cloud.firestore.annotation.DocumentId;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@Builder(toBuilder = true)
public class User implements UserDetails {
  @DocumentId
  private long id;

  @NotBlank
  @Email
  private String username;

  @NotBlank
  private String password;

  private Set<Role> roles;
  private boolean active;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return roles.stream()
        .map(role -> new SimpleGrantedAuthority("ROLE_" + role.name()))
        .collect(Collectors.toSet());
  }

  public static class UserBuilder {
    private Set<Role> roles; // Asegúrate de declarar este campo en el Builder

    public UserBuilder roles(Set<Role> roles) {
      this.roles = roles;
      return this;
    }

    public UserBuilder active(boolean active) {
      this.active = active;
      return this;
    }
  }

}
