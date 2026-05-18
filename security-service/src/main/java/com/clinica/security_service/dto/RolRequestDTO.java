package com.clinica.security_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class RolRequestDTO {

    @NotBlank(message = "El nombre del rol es obligatorio")
    @Size(max = 30, message = "El nombre del rol no puede superar los 30 caracteres")
    private String nombre;

    @NotBlank(message = "La descripcion del rol es obligatoria")
    @Size(max = 150, message = "La descripcion no puede superar los 150 caracteres")
    private String descripcion;

    public RolRequestDTO() {
    }

    public RolRequestDTO(String nombre, String descripcion) {
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
