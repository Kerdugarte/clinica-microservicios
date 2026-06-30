package com.clinica.ms_doctores.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class DoctorRequestDTO {

    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 2, max = 80, message = "El nombre debe tener entre 2 y 80 caracteres")
    private String nombre;

    @NotBlank(message = "El apellido es obligatorio")
    @Size(min = 2, max = 80, message = "El apellido debe tener entre 2 y 80 caracteres")
    private String apellido;

    @NotBlank(message = "El RUT es obligatorio")
    @Size(min = 7, max = 15, message = "El RUT debe tener entre 7 y 15 caracteres")
    private String rut;

    @NotBlank(message = "El email es obligatorio")
    @Email(message = "El email debe tener un formato valido")
    @Size(max = 120, message = "El email no puede superar los 120 caracteres")
    private String email;

    @NotBlank(message = "El telefono es obligatorio")
    @Size(min = 8, max = 20, message = "El telefono debe tener entre 8 y 20 caracteres")
    private String telefono;

    @NotBlank(message = "La especialidad es obligatoria")
    @Size(min = 3, max = 100, message = "La especialidad debe tener entre 3 y 100 caracteres")
    private String especialidad;

    @NotBlank(message = "El numero de registro es obligatorio")
    @Size(min = 3, max = 30, message = "El numero de registro debe tener entre 3 y 30 caracteres")
    private String numeroRegistro;

    public DoctorRequestDTO() {
    }

    public DoctorRequestDTO(String nombre, String apellido, String rut, String email, String telefono,
                            String especialidad, String numeroRegistro) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.rut = rut;
        this.email = email;
        this.telefono = telefono;
        this.especialidad = especialidad;
        this.numeroRegistro = numeroRegistro;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getRut() {
        return rut;
    }

    public void setRut(String rut) {
        this.rut = rut;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public String getNumeroRegistro() {
        return numeroRegistro;
    }

    public void setNumeroRegistro(String numeroRegistro) {
        this.numeroRegistro = numeroRegistro;
    }
}
