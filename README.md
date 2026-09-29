# Gestor de Tareas API

API REST construida con Spring Boot para gestionar proyectos y tareas: usuarios con
autenticación JWT, proyectos con un dueño, y tareas asignadas dentro de cada proyecto con
estado y fecha límite.

## Stack

- Java 21 + Spring Boot 4.1.1
- Spring Web (REST)
- Spring Data JPA + PostgreSQL
- Spring Security + JWT (jjwt 0.13.0)
- Docker Compose (levanta PostgreSQL automáticamente en desarrollo)
- Swagger / OpenAPI (springdoc-openapi) — documentación interactiva en `/swagger-ui/index.html`
- Lombok
- Bean Validation
- JUnit 5 + Mockito (pruebas unitarias de la capa de servicios)

## Estado actual

- [x] Modelo de dominio: `Usuario`, `Proyecto`, `Tarea` (con sus relaciones y enums `Rol`, `EstadoTarea`)
- [x] Repositorios (Spring Data JPA)
- [x] Servicios y lógica de negocio
- [x] Endpoints REST (controladores)
- [x] Autenticación y autorización (Spring Security + JWT)
- [x] Documentación de la API (Swagger/OpenAPI)
- [x] Pruebas automatizadas (JUnit + Mockito)

## Endpoints principales

| Método | Ruta | Acceso | Descripción |
|---|---|---|---|
| POST | `/api/usuarios/registro` | Público | Registra un usuario nuevo |
| POST | `/api/auth/login` | Público | Autentica y devuelve un token JWT |
| POST | `/api/proyectos` | Autenticado | Crea un proyecto |
| GET | `/api/proyectos/{id}` | Autenticado | Busca un proyecto por ID |
| GET | `/api/proyectos/usuario/{usuarioId}` | Autenticado | Lista los proyectos de un usuario |
| POST | `/api/tareas` | Autenticado | Crea una tarea (nace en estado `PENDIENTE`) |
| PATCH | `/api/tareas/{id}/asignar` | Autenticado | Asigna un usuario a una tarea |
| PATCH | `/api/tareas/{id}/estado` | Autenticado | Cambia el estado de una tarea |
| GET | `/api/tareas/proyecto/{proyectoId}` | Autenticado | Lista las tareas de un proyecto |
| GET | `/api/tareas/usuario/{usuarioId}` | Autenticado | Lista las tareas asignadas a un usuario |

Todas las rutas "Autenticado" requieren el header `Authorization: Bearer <token>`, obtenido
desde `/api/auth/login`. El archivo `requests.http` incluido en el repo trae ejemplos listos
para probar cada endpoint (incluyendo la captura automática del token tras el login).

## Autenticación

Login stateless con JWT: el token va firmado con HMAC-SHA, incluye el email y el rol del
usuario, y expira a las 24 horas. `JwtAuthenticationFilter` lo valida en cada petición y,
si falta o es inválido, `JwtAuthenticationEntryPoint` responde `401` con un mensaje JSON
claro (en vez del `403` genérico por defecto de Spring Security).

## Cómo ejecutar

Requiere Docker (para la base de datos, se levanta automáticamente al correr la app).

```
mvn spring-boot:run
```

O directamente desde IntelliJ IDEA, ejecutando la clase `GestorTareasApiApplication`.

Con la app corriendo, la documentación interactiva está en:
`http://localhost:8080/swagger-ui/index.html`

## Cómo correr las pruebas

```
mvn test
```

Las pruebas de la capa de servicios (`UsuarioServiceImplTest`, `ProyectoServiceImplTest`,
`TareaServiceImplTest`) son pruebas unitarias aisladas con Mockito, sin dependencias
externas. La prueba `GestorTareasApiApplicationTests.contextLoads` es una prueba de
integración que sí levanta la aplicación completa contra la base de datos real (por eso
requiere Docker corriendo).
