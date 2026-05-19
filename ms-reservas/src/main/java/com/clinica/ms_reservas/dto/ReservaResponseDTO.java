package com.clinica.ms_reservas.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public class ReservaResponseDTO {

    private Long id;
    private Long pacienteId;
    private Long doctorId;
    private LocalDate fechaReserva;
    private LocalTime horaReserva;
    private String motivo;
    private String estado;
    private Boolean activo;

    public ReservaResponseDTO() {
    }

    public ReservaResponseDTO(Long id, Long pacienteId, Long doctorId, LocalDate fechaReserva,
                              LocalTime horaReserva, String motivo, String estado, Boolean activo) {
        this.id = id;
        this.pacienteId = pacienteId;
        this.doctorId = doctorId;
        this.fechaReserva = fechaReserva;
        this.horaReserva = horaReserva;
        this.motivo = motivo;
        this.estado = estado;
        this.activo = activo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPacienteId() {
        return pacienteId;
    }

    public void setPacienteId(Long pacienteId) {
        this.pacienteId = pacienteId;
    }

    public Long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
    }

    public LocalDate getFechaReserva() {
        return fechaReserva;
    }

    public void setFechaReserva(LocalDate fechaReserva) {
        this.fechaReserva = fechaReserva;
    }

    public LocalTime getHoraReserva() {
        return horaReserva;
    }

    public void setHoraReserva(LocalTime horaReserva) {
        this.horaReserva = horaReserva;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }
}
