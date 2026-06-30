package com.clinica.ms_historial.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class HistorialRequestDTO {

    @NotNull(message = "El ID del paciente es obligatorio")
    private Long pacienteId;

    @NotNull(message = "El ID del doctor es obligatorio")
    private Long doctorId;

    @NotNull(message = "El ID de la reserva es obligatorio")
    private Long reservaId;

    @NotBlank(message = "El diagnostico es obligatorio")
    @Size(min = 5, max = 500, message = "El diagnostico debe tener entre 5 y 500 caracteres")
    private String diagnostico;

    @NotBlank(message = "El tratamiento es obligatorio")
    @Size(min = 5, max = 500, message = "El tratamiento debe tener entre 5 y 500 caracteres")
    private String tratamiento;

    @Size(max = 800, message = "Las observaciones no pueden superar los 800 caracteres")
    private String observaciones;

    public HistorialRequestDTO() {
    }

    public HistorialRequestDTO(Long pacienteId, Long doctorId, Long reservaId, String diagnostico,
                               String tratamiento, String observaciones) {
        this.pacienteId = pacienteId;
        this.doctorId = doctorId;
        this.reservaId = reservaId;
        this.diagnostico = diagnostico;
        this.tratamiento = tratamiento;
        this.observaciones = observaciones;
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

    public Long getReservaId() {
        return reservaId;
    }

    public void setReservaId(Long reservaId) {
        this.reservaId = reservaId;
    }

    public String getDiagnostico() {
        return diagnostico;
    }

    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }

    public String getTratamiento() {
        return tratamiento;
    }

    public void setTratamiento(String tratamiento) {
        this.tratamiento = tratamiento;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
}
