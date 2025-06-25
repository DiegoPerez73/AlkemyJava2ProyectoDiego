package com.Java2.proyecto.diego.AlkemyDiego.model;

import com.Java2.proyecto.diego.AlkemyDiego.enums.ProductType;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProductTest {

  @Test
  void testSettersAndGetters() {
    Product product = new Product();
    product.setId("p1");
    product.setMarca("Serenisima");
    product.setDescription("Leche entera");
    product.setProductoType(List.of(ProductType.LACTEO, ProductType.BEBIDA));
    product.setPrice(150.0);

    assertEquals("p1", product.getId());
    assertEquals("Serenisima", product.getMarca());
    assertEquals("Leche entera", product.getDescription());
    assertEquals(List.of(ProductType.LACTEO, ProductType.BEBIDA), product.getProductoType());
    assertEquals(150.0, product.getPrice());
  }

  @Test
  void testLombokDataEqualsAndHashCode() {
    Product p1 = new Product();
    p1.setId("p2");
    p1.setMarca("Coca Cola");
    p1.setDescription("Gaseosa");
    p1.setProductoType(List.of(ProductType.BEBIDA));
    p1.setPrice(300.0);

    Product p2 = new Product();
    p2.setId("p2");
    p2.setMarca("Coca Cola");
    p2.setDescription("Gaseosa");
    p2.setProductoType(List.of(ProductType.BEBIDA));
    p2.setPrice(300.0);

    assertEquals(p1, p2);
    assertEquals(p1.hashCode(), p2.hashCode());
  }

  @Test
  void testIsAvailable() {
    Product disponible = new Product();
    disponible.setId("p3");
    disponible.setMarca("MarcaX");
    disponible.setDescription("Producto disponible");
    disponible.setProductoType(List.of(ProductType.LACTEO));
    disponible.setPrice(10.0);

    Product noDisponible = new Product();
    noDisponible.setId("p4");
    noDisponible.setMarca("MarcaY");
    noDisponible.setDescription("Producto sin stock");
    noDisponible.setProductoType(List.of(ProductType.CARNE));
    noDisponible.setPrice(0.0);

    assertTrue(disponible.isAvailable());
    assertFalse(noDisponible.isAvailable());
  }
}