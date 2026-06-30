package com.clinica.ms_reportes.dto;

public class ReporteDetalleDTO {

    private String titulo;
    private String tipoReporte;
    private Long referenciaId;
    private Object datosPrincipales;
    private Object datosRelacionados;
    private String mensaje;

    public ReporteDetalleDTO() {
    }

    public ReporteDetalleDTO(String titulo, String tipoReporte, Long referenciaId,
                             Object datosPrincipales, Object datosRelacionados, String mensaje) {
        this.titulo = titulo;
        this.tipoReporte = tipoReporte;
        this.referenciaId = referenciaId;
        this.datosPrincipales = datosPrincipales;
        this.datosRelacionados = datosRelacionados;
        this.mensaje = mensaje;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
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

    public Object getDatosPrincipales() {
        return datosPrincipales;
    }

    public void setDatosPrincipales(Object datosPrincipales) {
        this.datosPrincipales = datosPrincipales;
    }

    public Object getDatosRelacionados() {
        return datosRelacionados;
    }

    public void setDatosRelacionados(Object datosRelacionados) {
        this.datosRelacionados = datosRelacionados;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}
