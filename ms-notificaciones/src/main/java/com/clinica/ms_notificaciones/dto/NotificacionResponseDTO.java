package com.clinica.ms_notificaciones.dto;

import java.time.LocalDateTime;

public class NotificacionResponseDTO {

    private Long id;
    private Long reservaId;
    private String destinatario;
    private String tipoNotificacion;
    private String mensaje;
    private String estado;
    private LocalDateTime fechaEnvio;
    private Boolean activo;

    public NotificacionResponseDTO() {
    }

    public NotificacionResponseDTO(Long id, Long reservaId, String destinatario, String tipoNotificacion,
                                   String mensaje, String estado, LocalDateTime fechaEnvio, Boolean activo) {
        this.id = id;
        this.reservaId = reservaId;
        this.destinatario = destinatario;
        this.tipoNotificacion = tipoNotificacion;
        this.mensaje = mensaje;
        this.estado = estado;
        this.fechaEnvio = fechaEnvio;
        this.activo = activo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getReservaId() {
        return reservaId;
    }

    public void setReservaId(Long reservaId) {
        this.reservaId = reservaId;
    }

    public String getDestinatario() {
        return destinatario;
    }

    public void setDestinatario(String destinatario) {
        this.destinatario = destinatario;
    }

    public String getTipoNotificacion() {
        return tipoNotificacion;
    }

    public void setTipoNotificacion(String tipoNotificacion) {
        this.tipoNotificacion = tipoNotificacion;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public LocalDateTime getFechaEnvio() {
        return fechaEnvio;
    }

    public void setFechaEnvio(LocalDateTime fechaEnvio) {
        this.fechaEnvio = fechaEnvio;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }
}
