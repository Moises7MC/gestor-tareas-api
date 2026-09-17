# Gestor de Tareas API

API REST construida con Spring Boot para gestionar proyectos y tareas: usuarios con
autenticación, proyectos con un dueño, y tareas asignadas dentro de cada proyecto con
estado y fecha límite.

## Stack

- Java 21 + Spring Boot 4.1.1
- Spring Web (REST)
- Spring Data JPA + PostgreSQL
- Spring Security + JWT (en progreso)
- Docker Compose (para levantar PostgreSQL automáticamente en desarrollo)
- Lombok
- Bean Validation

## Estado actual

En construcción. Hasta ahora:
- [x] Modelo de dominio: `Usuario`, `Proyecto`, `Tarea` (con sus relaciones y enums `Rol`, `EstadoTarea`)
- [ ] Repositorios (Spring Data JPA)
- [ ] Servicios y lógica de negocio
- [ ] Endpoints REST (controladores)
- [ ] Autenticación y autorización (Spring Security + JWT)
- [ ] Documentación de la API (Swagger/OpenAPI)
- [ ] Pruebas automatizadas (JUnit + Mockito)

## Cómo ejecutar

Requiere Docker (para la base de datos, se levanta automáticamente al correr la app).

```
mvn spring-boot:run
```

O directamente desde IntelliJ IDEA, ejecutando la clase `GestorTareasApiApplication`.
