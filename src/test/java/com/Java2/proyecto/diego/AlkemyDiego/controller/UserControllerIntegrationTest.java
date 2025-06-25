package com.Java2.proyecto.diego.AlkemyDiego.controller;

import com.Java2.proyecto.diego.AlkemyDiego.dto.UserDto;
import com.Java2.proyecto.diego.AlkemyDiego.enums.Role;
import com.Java2.proyecto.diego.AlkemyDiego.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private String testUserId;

    @AfterAll
    void cleanup() {
        if (testUserId != null) {
            userRepository.deleteById(testUserId);
        }
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void createUser_andGetUser() throws Exception {
        UserDto userDto = new UserDto();
        userDto.setName("Test User");
        userDto.setUsername("testuser@mail.com");
        userDto.setPassword("password123");
      userDto.setRoles(List.of("USER"));

        // Crear usuario
        var result = mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDto))
                .with(org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("testuser@mail.com"))
                .andReturn();

        // Guardar el id para limpiar después
        String response = result.getResponse().getContentAsString();
        UserDto created = objectMapper.readValue(response, UserDto.class);
        testUserId = created.getId();
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void createUser_andGetUserById() throws Exception {
        // Crear usuario
        UserDto userDto = new UserDto();
        userDto.setName("Test User");
        userDto.setUsername("testuser2@mail.com");
        userDto.setPassword("password123");
        userDto.setRoles(List.of("USER"));

        var result = mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDto))
                .with(org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isOk())
                .andReturn();

        String response = result.getResponse().getContentAsString();
        UserDto created = objectMapper.readValue(response, UserDto.class);
        String userId = created.getId();

        // Obtener usuario por ID
        mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get("/users/" + userId)
                .with(org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("testuser2@mail.com"));

        // Limpieza
        userRepository.deleteById(userId);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void createUser_andUpdateUser() throws Exception {
        UserDto userDto = new UserDto();
        userDto.setName("Nombre Original");
        userDto.setUsername("updateuser@mail.com");
        userDto.setPassword("password123");
        userDto.setRoles(List.of("USER"));

        var result = mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDto))
                .with(org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isOk())
                .andReturn();

        String response = result.getResponse().getContentAsString();
        UserDto created = objectMapper.readValue(response, UserDto.class);
        String userId = created.getId();

        userDto.setName("Nombre Actualizado");
        mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put("/users/" + userId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDto))
                .with(org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Nombre Actualizado"));

        mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get("/users/" + userId)
                .with(org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Nombre Actualizado"));

        userRepository.deleteById(userId);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void createUser_andDeleteUser() throws Exception {
        // Crear usuario
        UserDto userDto = new UserDto();
        userDto.setName("Delete User");
        userDto.setUsername("deleteuser@mail.com");
        userDto.setPassword("password123");
        userDto.setRoles(List.of("USER"));

        var result = mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDto))
                .with(org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isOk())
                .andReturn();

        String response = result.getResponse().getContentAsString();
        UserDto created = objectMapper.readValue(response, UserDto.class);
        String userId = created.getId();

        // Eliminar usuario
        mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete("/users/" + userId)
                .with(org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isNoContent());

        // Verificar que ya no existe
        mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get("/users/" + userId)
                .with(org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void createUsers_andListAllUsers() throws Exception {
        // Crear primer usuario
        UserDto user1 = new UserDto();
        user1.setName("User Uno");
        user1.setUsername("useruno@mail.com");
        user1.setPassword("password123");
        user1.setRoles(List.of("USER"));

        var result1 = mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user1))
                .with(org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isOk())
                .andReturn();
        String id1 = objectMapper.readValue(result1.getResponse().getContentAsString(), UserDto.class).getId();

        // Crear segundo usuario
        UserDto user2 = new UserDto();
        user2.setName("User Dos");
        user2.setUsername("userdos@mail.com");
        user2.setPassword("password123");
        user2.setRoles(List.of("USER"));

        var result2 = mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user2))
                .with(org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isOk())
                .andReturn();
        String id2 = objectMapper.readValue(result2.getResponse().getContentAsString(), UserDto.class).getId();

        // Listar usuarios y verificar que ambos estén presentes
        mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get("/users")
                .with(org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[?(@.id=='" + id1 + "')]").exists())
                .andExpect(jsonPath("$[?(@.id=='" + id2 + "')]").exists());

        // Limpieza
        userRepository.deleteById(id1);
        userRepository.deleteById(id2);
    }
}