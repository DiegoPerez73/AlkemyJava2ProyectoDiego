package com.Java2.proyecto.diego.AlkemyDiego.model;

import com.Java2.proyecto.diego.AlkemyDiego.enums.ProductType;
import com.google.cloud.firestore.annotation.DocumentId;
import lombok.Data;

import java.util.List;

@Data
public class Product {
  @DocumentId
  private String id;
  private String marca;
  private String description;
  private List<ProductType> productoType;
  private double price;

  public boolean isAvailable() {
    return price > 0;
  }
}