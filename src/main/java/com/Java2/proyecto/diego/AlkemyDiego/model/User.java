package com.Java2.proyecto.diego.AlkemyDiego.model;

import com.Java2.proyecto.diego.AlkemyDiego.enums.Role;
import com.google.cloud.firestore.annotation.DocumentId;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
public class User {
  @DocumentId
  private String id;

  @NotBlank
  private String name;

  @Email
  private String username;

  @Size(min = 8, max = 20)
  private String password;

  private List<Role> roles;
}
