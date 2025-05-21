package com.Java2.proyecto.diego.AlkemyDiego.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.io.InputStream;

@Configuration
public class FirestoreCfg {


  @Value("${spring.cloud.gcp.firestore.credentials.location}")
  private String credentialsPath;


  @Value("${spring.cloud.gcp.firestore.project-id}")
  private String projectId;


  @Bean
  public Firestore firestore() {
    if (FirebaseApp.getApps().isEmpty()) {
      try (InputStream serviceAccount = getClass()
          .getClassLoader()
          .getResourceAsStream(credentialsPath)) {
        if (serviceAccount == null) {
          throw new IllegalStateException("Credenciales no encontradas");
        }
        FirebaseOptions options = FirebaseOptions.builder()
            .setCredentials(GoogleCredentials.fromStream(serviceAccount))
            .setProjectId(projectId)
            .build();
        FirebaseApp.initializeApp(options);
      } catch (IOException e) {
        throw new RuntimeException("Error al inicializar Firestore", e);
      }
    }
    return FirestoreClient.getFirestore();
  }
}
