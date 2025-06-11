# Proyecto CRUD de Productos - Spring Boot + Firebase Firestore

Este proyecto es una aplicación backend desarrollada con **Spring Boot**, que implementa un sistema **CRUD de productos**,
incorporando **seguridad con JWT**, **registro y login de usuarios**, **concurrencia con ExecutorService**, **profiling con VisualVM** y **despliegue con Docker**.

## 🚀 Objetivo

El objetivo del proyecto es aplicar los conocimientos adquiridos en el bootcamp de Java 2, desarrollando una API REST
segura, testeada y que funcione con una base de datos NoSQL moderna.

## 🧱 Tecnologías utilizadas

- **Java 17**
- **Spring Boot**
- **Firebase Firestore** (NoSQL)
- **Spring Security** con autenticación **JWT**
- **JUnit 5** para pruebas unitarias
- **Maven** como gestor de dependencias
- **Docker** para contenerización y despliegue
- **VisualVM** para profiling y optimización de rendimiento
- **ExecutorService** y **CompletableFuture** para tareas concurrentes

## 🔐 Seguridad y autenticación

La aplicación cuenta con un sistema de autenticación con JWT (JSON Web Tokens), permitiendo proteger endpoints y
gestionar accesos. Incluye endpoints para **registro** y **login** de usuarios.

## ⚡ Concurrencia

Se implementan tareas asíncronas y concurrentes utilizando **ExecutorService** y **CompletableFuture**, por ejemplo para el registro y logueo de usuarios.

## 🐳 Docker

El proyecto incluye archivos `Dockerfile` y `docker-compose.yml` para facilitar la construcción y despliegue de la aplicación en contenedores Docker.

## 📊 Profiling

Se realizó análisis de rendimiento (CPU y memoria) utilizando **VisualVM** para detectar y optimizar posibles cuellos de botella.

## ✅ Tests unitarios

Se desarrollaron pruebas unitarias con **JUnit 5** para asegurar el correcto funcionamiento de los servicios y
controladores principales.

## 📦 Funcionalidades principales

- Crear un producto
- Obtener todos los productos
- Obtener un producto por ID
- Actualizar un producto
- Eliminar un producto
- Registro y login de usuarios con JWT
- Acceso concurrente y tareas en segundo plano

## ❗Consideraciones

- El archivo `adminsdk.json` no se puede subir a github por lo que se debe crear un archivo localmente.
- Las credenciales de Firebase deben ser configuradas en el archivo `application.properties`.

## 🧑‍💻 Autor

- Diego Pérez
- BBVA - Java 2 - Alkemy