package com.clinica.ms_pagos.service;

import com.clinica.ms_pagos.dto.PagoRequestDTO;
import com.clinica.ms_pagos.dto.PagoResponseDTO;

import java.util.List;

public interface PagoService {

    PagoResponseDTO crearPago(PagoRequestDTO requestDTO);

    List<PagoResponseDTO> listarPagos();

    PagoResponseDTO buscarPagoPorId(Long id);

    List<PagoResponseDTO> buscarPagosPorReserva(Long reservaId);

    List<PagoResponseDTO> buscarPagosPorEstado(String estadoPago);

    PagoResponseDTO actualizarPago(Long id, PagoRequestDTO requestDTO);

    PagoResponseDTO cambiarEstadoPago(Long id, String estadoPago);

    void eliminarPago(Long id);
}
