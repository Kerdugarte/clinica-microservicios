package com.clinica.security_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class PermisoRequestDTO {

    @NotBlank(message = "El nombre del permiso es obligatorio")
    @Size(max = 80, message = "El nombre del permiso no puede superar los 80 caracteres")
    private String nombre;

    @NotBlank(message = "La descripcion del permiso es obligatoria")
    @Size(max = 150, message = "La descripcion no puede superar los 150 caracteres")
    private String descripcion;

    public PermisoRequestDTO() {
    }

    public PermisoRequestDTO(String nombre, String descripcion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
