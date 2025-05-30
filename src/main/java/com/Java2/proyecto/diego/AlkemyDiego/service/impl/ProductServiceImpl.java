package com.Java2.proyecto.diego.AlkemyDiego.service.impl;

import com.Java2.proyecto.diego.AlkemyDiego.model.Product;
import com.Java2.proyecto.diego.AlkemyDiego.service.ProductService;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {
  private final Firestore firestore;

  @Override
  public String createProduct(Product product) {
    try {
      ApiFuture<WriteResult> result = firestore.collection("products").document(product.getId()).set(product);
      return "Producto guardado con ID: " + product.getId();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public List<Product> findAll() {
    try {
      ApiFuture<QuerySnapshot> querySnapshotApiFuture = firestore.collection("products").get();
      QuerySnapshot querySnapshot = querySnapshotApiFuture.get();
      List<Product> products = querySnapshot.getDocuments().stream().map(doc -> doc.toObject(Product.class)).toList();
      return products;
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public Product getProduct(String id) {
    try {
      ApiFuture<DocumentSnapshot> products = firestore.collection("products").document(id).get();
      return products.get().toObject(Product.class);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public String updateProduct(Product product) {
    try {
      ApiFuture<WriteResult> products = firestore.collection("products").document(product.getId()).update("description", product.getDescription());
      return "Producto actualizado: " + product.getDescription() + " En el horario: " + products.get().getUpdateTime();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public String deleteProduct(String id) {
    try {
      ApiFuture<WriteResult> products = firestore.collection("products").document(id).delete();
      return "Producto id " + id + " Borrado. En el horario: " + products.get().getUpdateTime();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

}