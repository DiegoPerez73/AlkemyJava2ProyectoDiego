package com.Java2.proyecto.diego.AlkemyDiego.service;

import com.Java2.proyecto.diego.AlkemyDiego.model.Product;

import java.util.List;

public interface ProductService {
  String createProduct(Product product);

  List<Product> findAll();

  Product getProduct(String id);

  String updateProduct(Product product);

  String deleteProduct(String id);
}