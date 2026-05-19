## Nombre del proyecto

Sistema de Gestión Clínica - Microservicios

## Integrantes

- Alejandro Rodríguez
- Leonardo Bravo
- Kerly Dugarte

## Descripción breve

Sistema desarrollado para la gestión de una clínica usando arquitectura de microservicios con Spring Boot, Eureka Server, API Gateway y MySQL.

## Tecnologías utilizadas

- Java
- Spring Boot
- Spring Cloud Eureka
- Spring Cloud Gateway
- Spring Data JPA
- Maven
- MySQL
- Postman
- GitHub

## Microservicios del proyecto

- Eureka Server
- API Gateway
- User Service
- Security Service
- MS Pacientes
- MS Doctores
- MS Reservas
- MS Pagos
- MS Notificaciones
- MS Reportes

## Rutas principales del sistema de microservicios

EUREKA SERVER
http://localhost:8761

API GATEWAY
http://localhost:8080

USER SERVICE
http://localhost:8081/api/v1/usuarios

SECURITY SERVICE
http://localhost:8082/api/v1/auth

MS PACIENTES
http://localhost:8083/api/v1/pacientes

MS DOCTORES
http://localhost:8084/api/v1/doctores

MS RESERVAS
http://localhost:8085/api/v1/reservas

MS PAGOS
http://localhost:8087/api/v1/pagos

MS NOTIFICACIONES
http://localhost:8088/api/v1/notificaciones

MS REPORTES
http://localhost:8089/api/v1/reportes


## Ejecución del proyecto

Para ejecutar el proyecto localmente se deben tener instaladas y configuradas las siguientes herramientas:

- Java 17
- Maven
- XAMPP
- MySQL
- VS Code o IntelliJ IDEA
- Postman

---

## 1. Iniciar XAMPP

Antes de ejecutar los microservicios, se debe abrir XAMPP e iniciar los servicios:

```text
Apache
MySQL

2. Crear las bases de datos

El proyecto incluye un script SQL general para crear las bases de datos y tablas necesarias.

El archivo se encuentra en:

database/init-all-databases.sql


## Orden para levantar el proyecto

1. Eureka Server
2. API Gateway
3. User Service
4. Security Service
5. MS Pacientes
6. MS Doctores
7. MS Reservas
8. MS Pagos
9. MS Notificaciones
10. MS Reportes
