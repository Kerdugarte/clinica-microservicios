# Sistema de Gestión Clínica - Microservicios

Sistema de gestión clínica desarrollado bajo una arquitectura de microservicios con Spring Boot, Maven Multi-Módulo, Eureka Server, API Gateway, OpenFeign, MySQL y documentación técnica mediante Swagger/OpenAPI.

Este proyecto permite administrar el flujo principal de una clínica: usuarios, autenticación, seguridad, pacientes, doctores, reservas médicas, historial clínico, pagos, notificaciones y reportes.

---

## Componentes de Distribución y Defensa Técnica

Utilice los siguientes enlaces externos para descargar las versiones listas para ejecución y visualizar la defensa técnica del proyecto:

| Componente                                                  | Descripción                                                                                                                                                                                             | Enlace de Descarga                                                                                                   |
| ----------------------------------------------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | -------------------------------------------------------------------------------------------------------------------- |
| **Versión Sin Docker** <br> _(Arranque Nativo)_             | Archivo `.zip` que contiene la carpeta `apps/` con los `.jar` compilados y el script `arrancar-nativo.bat` ordenado por fases. Requiere Java y MySQL configurados en el sistema anfitrión.              | [Descargar ZIP Nativo aquí](https://drive.google.com/drive/folders/13x152JpWNsmcG2xqxWB-G4QWQnSiFoHP?usp=share_link) |
| **Versión Con Docker** <br> _(Arranque con Docker Compose)_ | Archivo `.zip` que contiene la carpeta `apps/` con los `.jar`, el archivo `docker-compose.yml` y el script automatizado `arrancar-sistema.bat`. Solo requiere Docker Desktop en la máquina del cliente. | [Descargar ZIP Docker aquí](https://drive.google.com/drive/folders/1WpNGmqLK7fTkxeL0sG0EMWriqBncyZtL?usp=share_link) |
| **Video de Defensa Técnica** <br> _(Evaluación Individual)_ | Enlace directo al video explicativo donde se evidencia el funcionamiento, testing y aporte técnico individual. Duración ideal: 15 minutos. Máximo permitido: 18 minutos.                                | [Ver Video Explicativo aquí](https://drive.google.com/drive/folders/1bL8YtgMh19SNsn_vOS5VqClyyu_KwIf4?usp=share_link)                                  | ** Tablero Trello** | Gestión de tareas, roles, avances y evidencias del equipo. | [Ver tablero Trello aquí](https://trello.com/invite/b/6a047739214edba8891b6a4b/ATTIb4e6b2ac58c5ec519e631dc69c581c802F0D8944/proyecto-clinica) |                                  

---

## Integrantes

| Nombre              | Rol / Aporte Principal                                                      |
| ------------------- | --------------------------------------------------------------------------- |
| Alejandro Rodríguez | Desarrollo de microservicios, validaciones y apoyo en integración           |
| Leonardo Bravo      | Desarrollo de microservicios, base de datos y apoyo en documentación        |
| Kerly Dugarte       | Desarrollo de microservicios, documentación, pruebas y apoyo en integración |

---

## Objetivo del Proyecto

El objetivo del sistema es implementar una solución distribuida para la gestión clínica, separando las responsabilidades del negocio en microservicios independientes y comunicados entre sí mediante REST, Feign Client y API Gateway.

El sistema permite:

1. Registrar y administrar usuarios.
2. Gestionar autenticación y seguridad.
3. Registrar pacientes.
4. Registrar doctores.
5. Crear y consultar reservas médicas.
6. Administrar historial clínico.
7. Registrar pagos asociados a reservas.
8. Enviar notificaciones del sistema.
9. Generar reportes consolidados.
10. Centralizar el acceso mediante API Gateway.

---

## Arquitectura General

```text
Cliente / Postman / Swagger
        |
        v
API Gateway :8080
        |
        +--> auth-service          :8081
        +--> user-service          :8082
        +--> security-service      :8083
        +--> ms-pacientes          :8084
        +--> ms-doctores           :8085
        +--> ms-reservas           :8086
        +--> ms-pagos              :8087
        +--> ms-notificaciones     :8088
        +--> ms-reportes           :8089
        +--> ms-historial          :8090

Eureka Server :8761
MySQL          :3306
```

---

## Microservicios Implementados

| Microservicio       | Puerto | Responsabilidad                             |
| ------------------- | -----: | ------------------------------------------- |
| `eureka-server`     |   8761 | Registro y descubrimiento de microservicios |
| `api-gateway`       |   8080 | Punto único de entrada y enrutamiento       |
| `auth-service`      |   8081 | Autenticación de usuarios                   |
| `user-service`      |   8082 | Gestión de usuarios                         |
| `security-service`  |   8083 | Seguridad y validaciones relacionadas       |
| `ms-pacientes`      |   8084 | Gestión de pacientes                        |
| `ms-doctores`       |   8085 | Gestión de doctores                         |
| `ms-reservas`       |   8086 | Gestión de reservas médicas                 |
| `ms-pagos`          |   8087 | Gestión de pagos                            |
| `ms-notificaciones` |   8088 | Gestión de notificaciones                   |
| `ms-reportes`       |   8089 | Generación de reportes consolidados         |
| `ms-historial`      |   8090 | Gestión del historial clínico               |

---

## Rutas Principales mediante API Gateway

| Servicio          | Ruta Gateway                                  |
| ----------------- | --------------------------------------------- |
| Auth Service      | `http://localhost:8080/api/v1/auth`           |
| User Service      | `http://localhost:8080/api/v1/usuarios`       |
| Security Service  | `http://localhost:8080/api/v1/security`       |
| Pacientes         | `http://localhost:8080/api/v1/pacientes`      |
| Doctores          | `http://localhost:8080/api/v1/doctores`       |
| Reservas          | `http://localhost:8080/api/v1/reservas`       |
| Pagos             | `http://localhost:8080/api/v1/pagos`          |
| Notificaciones    | `http://localhost:8080/api/v1/notificaciones` |
| Reportes          | `http://localhost:8080/api/v1/reportes`       |
| Historial Clínico | `http://localhost:8080/api/v1/historiales`    |

---

## Eureka Server

Panel de Eureka:

```text
http://localhost:8761
```

En este panel se puede verificar que los microservicios estén registrados y disponibles.

---

## Documentación Swagger/OpenAPI

Cada microservicio de negocio expone su documentación técnica en Swagger UI.

| Servicio         | Swagger UI                                    |
| ---------------- | --------------------------------------------- |
| Auth Service     | `http://localhost:8081/swagger-ui/index.html` |
| User Service     | `http://localhost:8082/swagger-ui/index.html` |
| Security Service | `http://localhost:8083/swagger-ui/index.html` |
| Pacientes        | `http://localhost:8084/swagger-ui/index.html` |
| Doctores         | `http://localhost:8085/swagger-ui/index.html` |
| Reservas         | `http://localhost:8086/swagger-ui/index.html` |
| Pagos            | `http://localhost:8087/swagger-ui/index.html` |
| Notificaciones   | `http://localhost:8088/swagger-ui/index.html` |
| Reportes         | `http://localhost:8089/swagger-ui/index.html` |
| Historial        | `http://localhost:8090/swagger-ui/index.html` |

---

## Comunicación entre Microservicios

El sistema utiliza comunicación REST entre microservicios mediante OpenFeign.

| Servicio Origen | Servicio Destino | Objetivo                                |
| --------------- | ---------------- | --------------------------------------- |
| `ms-reservas`   | `ms-pacientes`   | Validar existencia del paciente         |
| `ms-reservas`   | `ms-doctores`    | Validar existencia del doctor           |
| `ms-pagos`      | `ms-reservas`    | Validar existencia de la reserva        |
| `ms-historial`  | `ms-pacientes`   | Asociar historial clínico a un paciente |
| `ms-reportes`   | `ms-pacientes`   | Obtener información de pacientes        |
| `ms-reportes`   | `ms-doctores`    | Obtener información de doctores         |
| `ms-reportes`   | `ms-reservas`    | Obtener información de reservas         |
| `ms-reportes`   | `ms-pagos`       | Obtener información de pagos            |

---

## Pruebas Unitarias

El proyecto contempla pruebas unitarias con JUnit 5 y Mockito para validar la lógica de negocio de los microservicios principales. Se incorporaron pruebas reales en servicios como pacientes, doctores, reservas, pagos, notificaciones, autenticación y usuarios.

Las pruebas deben cubrir:

- Servicios de negocio.
- Validaciones principales.
- Uso de mocks para repositorios o clientes Feign.
- Casos exitosos.
- Casos de error.
- Asserts claros.
- Estructura Given - When - Then.

Comando para ejecutar pruebas:

```bash
mvn clean test
```

Comando para compilar el proyecto completo:

```bash
mvn clean install
```

---

## Base de Datos

El proyecto utiliza MySQL como motor de base de datos.

El script principal se encuentra en:

```text
database/init-all-databases.sql
```

Este archivo permite crear las bases de datos necesarias para los microservicios.

---

## Orden de Ejecución Local

Para ejecutar el sistema de forma local, se recomienda seguir este orden:

1. Iniciar MySQL desde XAMPP o servicio local.
2. Ejecutar el script de base de datos `database/init-all-databases.sql`.
3. Levantar `eureka-server`.
4. Levantar los microservicios de soporte:
   - `auth-service`
   - `user-service`
   - `security-service`
5. Levantar los microservicios de negocio:
   - `ms-pacientes`
   - `ms-doctores`
   - `ms-historial`
   - `ms-reservas`
   - `ms-pagos`
   - `ms-notificaciones`
   - `ms-reportes`
6. Levantar `api-gateway`.
7. Probar las rutas mediante Postman, Swagger o navegador.

---

## Comandos de Ejecución por Módulo

Desde la raíz del proyecto:

```bash
mvn clean install
```

Ejecutar Eureka Server:

```bash
cd eureka-server
mvn spring-boot:run
```

Ejecutar API Gateway:

```bash
cd api-gateway
mvn spring-boot:run
```

Ejecutar un microservicio específico:

```bash
cd ms-pacientes
mvn spring-boot:run
```

---

## Flujo de Prueba Recomendado

Para validar el funcionamiento completo del ecosistema:

1. Verificar Eureka Server en `http://localhost:8761`.
2. Crear o consultar usuarios.
3. Crear paciente.
4. Crear doctor.
5. Crear reserva médica asociada a paciente y doctor.
6. Crear pago asociado a la reserva.
7. Registrar historial clínico del paciente.
8. Enviar o consultar notificación.
9. Generar reporte consolidado.
10. Validar las rutas desde API Gateway.

---

## Buenas Prácticas Aplicadas

- Arquitectura Maven Multi-Módulo.
- Separación por capas Controller, Service, Repository, Model y DTO.
- Uso de API Gateway como punto único de entrada.
- Registro de microservicios mediante Eureka Server.
- Comunicación REST entre microservicios mediante Feign Client.
- Configuración independiente por microservicio.
- Centralización de rutas.
- Separación funcional por dominio.
- Uso de GitHub para control de versiones.
- Distribución de `.jar` y archivos de ejecución mediante Google Drive, evitando subir binarios al repositorio.
- Exclusión de archivos binarios y carpetas `target/` mediante `.gitignore`.

---

## Archivos Excluidos del Repositorio

Por buenas prácticas de control de versiones, este repositorio no debe incluir:

```text
target/
*.jar
*.log
.idea/
.vscode/
.DS_Store
```

Los archivos `.jar` compilados se distribuyen únicamente mediante enlaces externos de Google Drive en los ZIP de entrega.

---

## Tecnologías Utilizadas

- Java 17
- Spring Boot
- Spring Web
- Spring Data JPA
- Spring Cloud Gateway
- Spring Cloud Netflix Eureka
- OpenFeign
- MySQL
- Maven Multi-Módulo
- JUnit 5
- Mockito
- Swagger/OpenAPI
- GitHub

---

## Estado de Entrega

| Elemento                         | Estado                       |
| -------------------------------- | ---------------------------- |
| Código fuente Maven Multi-Módulo | Implementado                 |
| API Gateway                      | Implementado                 |
| Eureka Server                    | Implementado                 |
| Microservicios de negocio        | Implementados                |
| Comunicación Feign               | Implementada parcialmente    |
| Swagger/OpenAPI                  | Implementado                 |
| Pruebas unitarias                | Implementadas y fortalecidas |
| Docker Compose                   | Implementado                 |
| ZIP Nativo                       | Implementado                 |
| ZIP Docker                       | Implementado                 |
| Video defensa                    | Pendiente                    |
