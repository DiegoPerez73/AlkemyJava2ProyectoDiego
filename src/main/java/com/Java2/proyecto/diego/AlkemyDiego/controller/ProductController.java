package com.Java2.proyecto.diego.AlkemyDiego.controller;

import com.Java2.proyecto.diego.AlkemyDiego.model.Product;
import com.Java2.proyecto.diego.AlkemyDiego.service.impl.ProductServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@AllArgsConstructor
public class ProductController {
  private final ProductServiceImpl productServiceImpl;

  @GetMapping
  public ResponseEntity<List<Product>> findAll() {
    List<Product> users = productServiceImpl.findAll();
    return ResponseEntity.ok(users);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Product> getProduct(@PathVariable("id") String id) {
    Product product = productServiceImpl.getProduct(id);
    return ResponseEntity.ok(product);
  }

  @PostMapping("/create")
  public ResponseEntity<String> createProduct(@RequestBody Product product) {
    return ResponseEntity.ok(productServiceImpl.createProduct(product));
  }

  @PutMapping("/modify")
  public ResponseEntity<String> updateProduct(@RequestBody Product product) {
    return ResponseEntity.ok(productServiceImpl.updateProduct(product));
  }

  @DeleteMapping("/delete")
  public ResponseEntity<String> deleteProduct(@RequestBody String id) {
    return ResponseEntity.ok(productServiceImpl.deleteProduct(id));
  }

}