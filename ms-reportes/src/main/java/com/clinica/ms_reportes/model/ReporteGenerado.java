package com.clinica.ms_reportes.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "reportes_generados")
public class ReporteGenerado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 80)
    private String tipoReporte;

    private Long referenciaId;

    @Column(nullable = false)
    private LocalDateTime fechaGeneracion = LocalDateTime.now();

    @Column(nullable = false, length = 300)
    private String descripcion;

    @Column(nullable = false)
    private Boolean activo = true;

    public ReporteGenerado() {
    }

    public ReporteGenerado(Long id, String tipoReporte, Long referenciaId, LocalDateTime fechaGeneracion,
                           String descripcion, Boolean activo) {
        this.id = id;
        this.tipoReporte = tipoReporte;
        this.referenciaId = referenciaId;
        this.fechaGeneracion = fechaGeneracion;
        this.descripcion = descripcion;
        this.activo = activo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipoReporte() {
        return tipoReporte;
    }

    public void setTipoReporte(String tipoReporte) {
        this.tipoReporte = tipoReporte;
    }

    public Long getReferenciaId() {
        return referenciaId;
    }

    public void setReferenciaId(Long referenciaId) {
        this.referenciaId = referenciaId;
    }

    public LocalDateTime getFechaGeneracion() {
        return fechaGeneracion;
    }

    public void setFechaGeneracion(LocalDateTime fechaGeneracion) {
        this.fechaGeneracion = fechaGeneracion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }
}
