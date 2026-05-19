package com.clinica.ms_reservas.service;

import com.clinica.ms_reservas.dto.ReservaRequestDTO;
import com.clinica.ms_reservas.dto.ReservaResponseDTO;

import java.util.List;

public interface ReservaService {

    ReservaResponseDTO crearReserva(ReservaRequestDTO requestDTO);

    List<ReservaResponseDTO> listarReservas();

    ReservaResponseDTO buscarReservaPorId(Long id);

    List<ReservaResponseDTO> buscarReservasPorPaciente(Long pacienteId);

    List<ReservaResponseDTO> buscarReservasPorDoctor(Long doctorId);

    List<ReservaResponseDTO> buscarReservasPorEstado(String estado);

    ReservaResponseDTO actualizarReserva(Long id, ReservaRequestDTO requestDTO);

    ReservaResponseDTO cambiarEstadoReserva(Long id, String estado);

    void eliminarReserva(Long id);
}
