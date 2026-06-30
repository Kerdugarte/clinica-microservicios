package com.clinica.ms_pagos.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public class PagoRequestDTO {

    @NotNull(message = "El ID de la reserva es obligatorio")
    private Long reservaId;

    @NotNull(message = "El monto es obligatorio")
    @DecimalMin(value = "1.0", message = "El monto debe ser mayor a 0")
    private BigDecimal monto;

    @NotBlank(message = "El metodo de pago es obligatorio")
    @Size(min = 3, max = 50, message = "El metodo de pago debe tener entre 3 y 50 caracteres")
    private String metodoPago;

    @Size(max = 30, message = "El estado del pago no puede superar los 30 caracteres")
    private String estadoPago;

    public PagoRequestDTO() {
    }

    public PagoRequestDTO(Long reservaId, BigDecimal monto, String metodoPago, String estadoPago) {
        this.reservaId = reservaId;
        this.monto = monto;
        this.metodoPago = metodoPago;
        this.estadoPago = estadoPago;
    }

    public Long getReservaId() {
        return reservaId;
    }

    public void setReservaId(Long reservaId) {
        this.reservaId = reservaId;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

    public String getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(String metodoPago) {
        this.metodoPago = metodoPago;
    }

    public String getEstadoPago() {
        return estadoPago;
    }

    public void setEstadoPago(String estadoPago) {
        this.estadoPago = estadoPago;
    }
}
