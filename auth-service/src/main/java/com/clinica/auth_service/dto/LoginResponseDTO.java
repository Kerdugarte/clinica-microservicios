package com.clinica.auth_service.dto;

public class LoginResponseDTO {

    private String email;
    private String rol;
    private String token;
    private String mensaje;

    public LoginResponseDTO() {
    }

    public LoginResponseDTO(String email, String rol, String token, String mensaje) {
        this.email = email;
        this.rol = rol;
        this.token = token;
        this.mensaje = mensaje;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}
