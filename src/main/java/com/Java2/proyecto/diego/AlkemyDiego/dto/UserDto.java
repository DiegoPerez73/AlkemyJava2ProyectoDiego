package com.Java2.proyecto.diego.AlkemyDiego.dto;

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

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
public class UserDto {
  @DocumentId
  private String id;

  @NotBlank
  private String name;

  @Email
  private String username;

  @Size(min = 8, max = 20)
  private String password;

  private List<String> roles;
}
