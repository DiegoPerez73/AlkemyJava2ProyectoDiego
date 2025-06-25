package com.Java2.proyecto.diego.AlkemyDiego.service;

import org.junit.jupiter.api.Test;
import java.util.concurrent.CompletableFuture;
import static org.junit.jupiter.api.Assertions.*;

class AsyncTaskServiceTest {

    private final AsyncTaskService service = new AsyncTaskService();

    @Test
    void testLogUserRegistrationCompletes() throws Exception {
        CompletableFuture<Void> future = service.logUserRegistration("usuarioTest");
        future.get(); // Espera a que termine
        assertTrue(future.isDone());
        assertFalse(future.isCompletedExceptionally());
    }

    @Test
    void testLogUserLoginCompletes() throws Exception {
        CompletableFuture<Void> future = service.logUserLogin("usuarioTest");
        future.get(); // Espera a que termine
        assertTrue(future.isDone());
        assertFalse(future.isCompletedExceptionally());
    }
}