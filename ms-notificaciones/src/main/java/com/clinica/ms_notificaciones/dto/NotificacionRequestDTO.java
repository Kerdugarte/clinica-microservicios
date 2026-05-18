package com.clinica.ms_notificaciones.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class NotificacionRequestDTO {

    @NotNull(message = "El ID de la reserva es obligatorio")
    private Long reservaId;

    @NotBlank(message = "El destinatario es obligatorio")
    @Size(min = 5, max = 120, message = "El destinatario debe tener entre 5 y 120 caracteres")
    private String destinatario;

    @NotBlank(message = "El tipo de notificacion es obligatorio")
    @Size(min = 3, max = 50, message = "El tipo de notificacion debe tener entre 3 y 50 caracteres")
    private String tipoNotificacion;

    @NotBlank(message = "El mensaje es obligatorio")
    @Size(min = 5, max = 500, message = "El mensaje debe tener entre 5 y 500 caracteres")
    private String mensaje;

    @Size(max = 30, message = "El estado no puede superar los 30 caracteres")
    private String estado;

    public NotificacionRequestDTO() {
    }

    public NotificacionRequestDTO(Long reservaId, String destinatario, String tipoNotificacion,
                                  String mensaje, String estado) {
        this.reservaId = reservaId;
        this.destinatario = destinatario;
        this.tipoNotificacion = tipoNotificacion;
        this.mensaje = mensaje;
        this.estado = estado;
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
}
