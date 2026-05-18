package com.clinica.ms_reportes.dto;

public class ReporteResumenDTO {

    private String titulo;
    private String descripcion;
    private Integer totalPacientes;
    private Integer totalDoctores;
    private Integer totalReservas;
    private Integer totalPagos;
    private Integer totalHistoriales;

    public ReporteResumenDTO() {
    }

    public ReporteResumenDTO(String titulo, String descripcion, Integer totalPacientes, Integer totalDoctores,
                             Integer totalReservas, Integer totalPagos, Integer totalHistoriales) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.totalPacientes = totalPacientes;
        this.totalDoctores = totalDoctores;
        this.totalReservas = totalReservas;
        this.totalPagos = totalPagos;
        this.totalHistoriales = totalHistoriales;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getTotalPacientes() {
        return totalPacientes;
    }

    public void setTotalPacientes(Integer totalPacientes) {
        this.totalPacientes = totalPacientes;
    }

    public Integer getTotalDoctores() {
        return totalDoctores;
    }

    public void setTotalDoctores(Integer totalDoctores) {
        this.totalDoctores = totalDoctores;
    }

    public Integer getTotalReservas() {
        return totalReservas;
    }

    public void setTotalReservas(Integer totalReservas) {
        this.totalReservas = totalReservas;
    }

    public Integer getTotalPagos() {
        return totalPagos;
    }

    public void setTotalPagos(Integer totalPagos) {
        this.totalPagos = totalPagos;
    }

    public Integer getTotalHistoriales() {
        return totalHistoriales;
    }

    public void setTotalHistoriales(Integer totalHistoriales) {
        this.totalHistoriales = totalHistoriales;
    }
}
