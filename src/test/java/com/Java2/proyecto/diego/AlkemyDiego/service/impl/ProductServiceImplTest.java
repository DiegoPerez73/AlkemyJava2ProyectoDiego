package com.Java2.proyecto.diego.AlkemyDiego.service.impl;

import com.Java2.proyecto.diego.AlkemyDiego.model.Product;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(org.mockito.junit.jupiter.MockitoExtension.class)
class ProductServiceImplTest {

  @Mock
  private Firestore firestore;
  @Mock
  private CollectionReference collectionReference;
  @Mock
  private DocumentReference documentReference;
  @Mock
  private ApiFuture<WriteResult> apiFuture;
  @Mock
  private ApiFuture<QuerySnapshot> querySnapshotApiFuture;
  @Mock
  private QuerySnapshot querySnapshot;
  @Mock
  private DocumentSnapshot documentSnapshot;
  @Mock
  private ApiFuture<DocumentSnapshot> documentSnapshotApiFuture;

  @InjectMocks
  private ProductServiceImpl productService;

  @Test
  void testCreateProduct() throws Exception {
    Product product = new Product();
    product.setId("123");
    when(firestore.collection("products")).thenReturn(collectionReference);
    when(collectionReference.document("123")).thenReturn(documentReference);
    when(documentReference.set(product)).thenReturn(apiFuture);

    String result = productService.createProduct(product);

    verify(documentReference).set(product);
    assertEquals("Producto guardado con ID: 123", result);
  }


  @Mock
  private QueryDocumentSnapshot queryDocumentSnapshot; // Agrega este mock

  @Test
  void testFindAll() throws Exception {
    Product product = new Product();
    product.setId("1");
    List<QueryDocumentSnapshot> docs = List.of(queryDocumentSnapshot);

    when(firestore.collection("products")).thenReturn(collectionReference);
    when(collectionReference.get()).thenReturn(querySnapshotApiFuture);
    when(querySnapshotApiFuture.get()).thenReturn(querySnapshot);
    doReturn(docs).when(querySnapshot).getDocuments();
    when(queryDocumentSnapshot.toObject(Product.class)).thenReturn(product);

    List<Product> result = productService.findAll();

    assertEquals(1, result.size());
    assertEquals(product, result.get(0));
  }

  @Test
  void testGetProduct() throws Exception {
    Product product = new Product();
    product.setId("1");

    when(firestore.collection("products")).thenReturn(collectionReference);
    when(collectionReference.document("1")).thenReturn(documentReference);
    when(documentReference.get()).thenReturn(documentSnapshotApiFuture);
    when(documentSnapshotApiFuture.get()).thenReturn(documentSnapshot);
    when(documentSnapshot.toObject(Product.class)).thenReturn(product);

    Product result = productService.getProduct("1");

    assertEquals(product, result);
  }

  @Test
  void testUpdateProduct() throws Exception {
    Product product = new Product();
    product.setId("1");
    product.setDescription("Nueva descripción");

    WriteResult writeResult = mock(WriteResult.class);
    when(writeResult.getUpdateTime()).thenReturn(com.google.cloud.Timestamp.now());

    when(firestore.collection("products")).thenReturn(collectionReference);
    when(collectionReference.document("1")).thenReturn(documentReference);
    when(documentReference.update("description", "Nueva descripción")).thenReturn(apiFuture);
    when(apiFuture.get()).thenReturn(writeResult);

    String result = productService.updateProduct(product);

    assertTrue(result.contains("Producto actualizado: Nueva descripción"));
  }

  @Test
  void testDeleteProduct() throws Exception {
    WriteResult writeResult = mock(WriteResult.class);
    when(writeResult.getUpdateTime()).thenReturn(com.google.cloud.Timestamp.now());

    when(firestore.collection("products")).thenReturn(collectionReference);
    when(collectionReference.document("1")).thenReturn(documentReference);
    when(documentReference.delete()).thenReturn(apiFuture);
    when(apiFuture.get()).thenReturn(writeResult);

    String result = productService.deleteProduct("1");

    assertTrue(result.contains("Producto id 1 Borrado."));
  }
}