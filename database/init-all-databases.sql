-- =====================================================
-- SCRIPT GENERAL DE BASES DE DATOS
-- Proyecto Clínica Microservicios
-- Cada microservicio tiene su propia base de datos
-- =====================================================

CREATE DATABASE IF NOT EXISTS db_usuarios;
CREATE DATABASE IF NOT EXISTS db_auth;
CREATE DATABASE IF NOT EXISTS db_security;
CREATE DATABASE IF NOT EXISTS db_pacientes;
CREATE DATABASE IF NOT EXISTS db_doctores;
CREATE DATABASE IF NOT EXISTS db_reservas;
CREATE DATABASE IF NOT EXISTS db_pagos;
CREATE DATABASE IF NOT EXISTS db_notificaciones;
CREATE DATABASE IF NOT EXISTS db_reportes;
CREATE DATABASE IF NOT EXISTS db_historial;

-- =====================================================
-- USER-SERVICE
-- Base de datos: db_usuarios
-- =====================================================

USE db_usuarios;

DROP TABLE IF EXISTS usuarios;

CREATE TABLE usuarios (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(80) NOT NULL,
    apellido VARCHAR(80) NOT NULL,
    email VARCHAR(120) NOT NULL UNIQUE,
    rol VARCHAR(30) NOT NULL,
    activo BOOLEAN NOT NULL
);

-- =====================================================
-- AUTH-SERVICE
-- Base de datos: db_auth
-- =====================================================

USE db_auth;

DROP TABLE IF EXISTS credenciales;

CREATE TABLE credenciales (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(120) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    rol VARCHAR(30) NOT NULL,
    activo BOOLEAN NOT NULL
);

-- =====================================================
-- SECURITY-SERVICE
-- Base de datos: db_security
-- =====================================================

USE db_security;

DROP TABLE IF EXISTS roles;

CREATE TABLE roles (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL UNIQUE,
    descripcion VARCHAR(255),
    activo BOOLEAN NOT NULL
);

DROP TABLE IF EXISTS permisos;

CREATE TABLE permisos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(80) NOT NULL UNIQUE,
    descripcion VARCHAR(255),
    activo BOOLEAN NOT NULL
);

-- =====================================================
-- MS-PACIENTES
-- Base de datos: db_pacientes
-- =====================================================

USE db_pacientes;

DROP TABLE IF EXISTS pacientes;

CREATE TABLE pacientes (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(80) NOT NULL,
    apellido VARCHAR(80) NOT NULL,
    rut VARCHAR(20) NOT NULL UNIQUE,
    email VARCHAR(120) NOT NULL UNIQUE,
    fecha_nacimiento DATE NOT NULL,
    telefono VARCHAR(30),
    activo BOOLEAN NOT NULL
);

-- =====================================================
-- MS-DOCTORES
-- Base de datos: db_doctores
-- =====================================================

USE db_doctores;

DROP TABLE IF EXISTS doctores;

CREATE TABLE doctores (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(80) NOT NULL,
    apellido VARCHAR(80) NOT NULL,
    rut VARCHAR(20) NOT NULL UNIQUE,
    email VARCHAR(120) NOT NULL UNIQUE,
    telefono VARCHAR(30),
    especialidad VARCHAR(120) NOT NULL,
    activo BOOLEAN NOT NULL
);

-- =====================================================
-- MS-RESERVAS
-- Base de datos: db_reservas
-- =====================================================

USE db_reservas;

DROP TABLE IF EXISTS reservas;

CREATE TABLE reservas (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    paciente_id BIGINT NOT NULL,
    doctor_id BIGINT NOT NULL,
    fecha_reserva DATE NOT NULL,
    hora_reserva TIME NOT NULL,
    motivo VARCHAR(255) NOT NULL,
    estado VARCHAR(30) NOT NULL,
    activo BOOLEAN NOT NULL
);

-- =====================================================
-- MS-PAGOS
-- Base de datos: db_pagos
-- =====================================================

USE db_pagos;

DROP TABLE IF EXISTS pagos;

CREATE TABLE pagos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    reserva_id BIGINT NOT NULL,
    monto DECIMAL(12,2) NOT NULL,
    metodo_pago VARCHAR(50) NOT NULL,
    estado_pago VARCHAR(30) NOT NULL,
    fecha_pago DATETIME NOT NULL,
    activo BOOLEAN NOT NULL
);

-- =====================================================
-- MS-NOTIFICACIONES
-- Base de datos: db_notificaciones
-- =====================================================

USE db_notificaciones;

DROP TABLE IF EXISTS notificaciones;

CREATE TABLE notificaciones (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    reserva_id BIGINT NOT NULL,
    destinatario VARCHAR(120) NOT NULL,
    tipo_notificacion VARCHAR(50) NOT NULL,
    mensaje VARCHAR(500) NOT NULL,
    estado VARCHAR(30) NOT NULL,
    fecha_envio DATETIME NOT NULL,
    activo BOOLEAN NOT NULL
);

-- =====================================================
-- MS-HISTORIAL
-- Base de datos: db_historial
-- =====================================================

USE db_historial;

DROP TABLE IF EXISTS historiales;

CREATE TABLE historiales (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    paciente_id BIGINT NOT NULL,
    doctor_id BIGINT NOT NULL,
    reserva_id BIGINT NOT NULL,
    diagnostico VARCHAR(500) NOT NULL,
    tratamiento VARCHAR(500) NOT NULL,
    observaciones VARCHAR(800),
    fecha_registro DATETIME NOT NULL,
    activo BOOLEAN NOT NULL
);

-- =====================================================
-- MS-REPORTES
-- Base de datos: db_reportes
-- =====================================================

USE db_reportes;

DROP TABLE IF EXISTS reportes_generados;

CREATE TABLE reportes_generados (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    tipo_reporte VARCHAR(80) NOT NULL,
    referencia_id BIGINT,
    fecha_generacion DATETIME NOT NULL,
    descripcion VARCHAR(300) NOT NULL,
    activo BOOLEAN NOT NULL
);