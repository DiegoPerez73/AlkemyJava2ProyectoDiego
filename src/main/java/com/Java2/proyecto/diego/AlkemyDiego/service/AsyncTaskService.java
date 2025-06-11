package com.Java2.proyecto.diego.AlkemyDiego.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@Service
public class AsyncTaskService {
  private final ExecutorService executor = Executors.newFixedThreadPool(2);

  public CompletableFuture<Void> logUserRegistration(String username) {
    return CompletableFuture.runAsync(() -> {
      log.info("Usuario registrado: {}", username);
      // Simula una tarea que toma tiempo, como guardar en la base de datos
      try {
        Thread.sleep(2000);
      } catch (InterruptedException ignored) {
      }
    }, executor);
  }

  public CompletableFuture<Void> logUserLogin(String username) {
    return CompletableFuture.runAsync(() -> {
      log.info("Usuario logueado: {}", username);
    });
  }
}