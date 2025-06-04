package com.Java2.proyecto.diego.AlkemyDiego.repository;

import com.Java2.proyecto.diego.AlkemyDiego.model.User;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

@Repository
public class UserRepository {

    @Autowired
    private Firestore firestore;

    public Optional<User> findByUsername(String username) {
        CollectionReference users = firestore.collection("users");
        Query query = users.whereEqualTo("username", username).limit(1);
        ApiFuture<QuerySnapshot> querySnapshot = query.get();
        try {
            for (DocumentSnapshot doc : querySnapshot.get().getDocuments()) {
                User user = doc.toObject(User.class);
                return Optional.ofNullable(user);
            }
        } catch (InterruptedException | ExecutionException e) {
            Thread.currentThread().interrupt();
        }
        return Optional.empty();
    }

    public boolean existsByUsername(String username) {
        return findByUsername(username).isPresent();
    }

  public void save(User user) {
    CollectionReference users = firestore.collection("users");
    if (user.getId() == null || user.getId().isEmpty()) {
      user.setId(users.document().getId());
    }
    users.document(user.getId()).set(user);
  }

  public List<User> findAll() {
    CollectionReference users = firestore.collection("users");
    ApiFuture<QuerySnapshot> querySnapshot = users.get();
    try {
      return querySnapshot.get().getDocuments().stream()
          .map(doc -> doc.toObject(User.class))
          .toList();
    } catch (InterruptedException | ExecutionException e) {
      Thread.currentThread().interrupt();
      return List.of();
    }
  }

  public Optional<User> findById(String id) {
    CollectionReference users = firestore.collection("users");
    ApiFuture<DocumentSnapshot> docSnap = users.document(id).get();
    try {
      DocumentSnapshot doc = docSnap.get();
      if (doc.exists()) {
        return Optional.ofNullable(doc.toObject(User.class));
      }
    } catch (InterruptedException | ExecutionException e) {
      Thread.currentThread().interrupt();
    }
    return Optional.empty();
  }

  public void deleteById(String id) {
    firestore.collection("users").document(id).delete();
  }
}