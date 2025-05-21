# Proyecto CRUD de Productos - Spring Boot + Firebase Firestore

Este proyecto es una aplicación backend desarrollada con **Spring Boot**, que implementa un sistema **CRUD de productos**,
incorporando **seguridad**, **tests unitarios** y utilizando **Firebase Firestore** como base de datos no
relacional.

## 🚀 Objetivo

El objetivo del proyecto es aplicar los conocimientos adquiridos en el bootcamp de Java 2, desarrollando una API REST
segura, testeada y que funcione con una base de datos NoSQL moderna.

## 🧱 Tecnologías utilizadas

- **Java 17**
- **Spring Boot**
- **Firebase Firestore** (NoSQL)
- **Spring Security**
- **JUnit 5** para pruebas unitarias
- **Maven** como gestor de dependencias

## 🗄️ ¿Por qué elegí Firestore?

Decidí utilizar **Firebase Firestore** en lugar de una base de datos tradicional como MongoDB o relacional como MySQL
por las siguientes razones:

- Me interesa su **integración con aplicaciones en tiempo real**, especialmente del lado del frontend.
- Me gustó su **modelo de documentos y colecciones**, el cual me pareció más simple y ágil para muchos casos de uso.
- Quise **salir del paradigma SQL**, y aprender más sobre **bases de datos no relacionales** y su funcionamiento.

## 🔐 Seguridad

La aplicación cuenta con un sistema de autenticación con JWT (JSON Web Tokens), permitiendo proteger endpoints y
gestionar accesos.

## ✅ Tests unitarios

Se desarrollaron pruebas unitarias con **JUnit 5** para asegurar el correcto funcionamiento de los servicios y
controladores principales.

## 📦 Funcionalidades principales

- Crear un producto
- Obtener todos los productos
- Obtener un producto por ID
- Actualizar un producto
- Eliminar un producto

## ❗Consideraciones

- El archivo adminsdk.json no se puede subir a github por lo que se debe crear un archivo
- Las credenciales de Firebase deben ser configuradas en el archivo `application.properties`.

## 🧑‍💻 Autor

- Diego Pérez
- BBVA - Java 2 - Alkemy

