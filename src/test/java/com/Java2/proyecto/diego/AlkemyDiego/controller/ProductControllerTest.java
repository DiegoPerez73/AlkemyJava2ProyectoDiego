package com.Java2.proyecto.diego.AlkemyDiego.controller;

import com.Java2.proyecto.diego.AlkemyDiego.model.Product;
import com.Java2.proyecto.diego.AlkemyDiego.service.impl.ProductServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

@WebMvcTest(ProductController.class)
@Import(ProductControllerTest.MockConfig.class)
class ProductControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ProductServiceImpl productServiceImpl;

  @TestConfiguration
  static class MockConfig {
    @Bean
    public ProductServiceImpl productServiceImpl() {
      return Mockito.mock(ProductServiceImpl.class);
    }
  }

  @Test
  @WithMockUser
  void findAll_returnsListOfProducts() throws Exception {
    Product product = new Product();
    Mockito.when(productServiceImpl.findAll()).thenReturn(Collections.singletonList(product));

    mockMvc.perform(get("/products")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$").isArray());
  }

  @Test
  @WithMockUser
  void getProduct_returnsProduct() throws Exception {
    Product product = new Product();
    Mockito.when(productServiceImpl.getProduct("1")).thenReturn(product);

    mockMvc.perform(get("/products/1")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
  }

  @Test
  @WithMockUser(roles = "ADMIN")
  void createProduct_returnsOk() throws Exception {
    Mockito.when(productServiceImpl.createProduct(any(Product.class))).thenReturn("created");

    mockMvc.perform(post("/products/create")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{}")
            .with(csrf()))
        .andExpect(status().isOk());
  }

  @Test
  @WithMockUser(roles = "ADMIN")
  void updateProduct_returnsOk() throws Exception {
    Mockito.when(productServiceImpl.updateProduct(any(Product.class))).thenReturn("updated");

    mockMvc.perform(put("/products/modify")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{}")
            .with(csrf()))
        .andExpect(status().isOk());
  }

  @Test
  @WithMockUser(roles = "ADMIN")
  void deleteProduct_returnsOk() throws Exception {
    Mockito.when(productServiceImpl.deleteProduct(any(String.class))).thenReturn("deleted");

    mockMvc.perform(delete("/products/delete")
            .contentType(MediaType.APPLICATION_JSON)
            .content("\"1\"")
            .with(csrf()))
        .andExpect(status().isOk());
  }
}