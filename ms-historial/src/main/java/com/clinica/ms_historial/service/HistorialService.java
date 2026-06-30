package com.clinica.ms_historial.service;

import com.clinica.ms_historial.dto.HistorialRequestDTO;
import com.clinica.ms_historial.dto.HistorialResponseDTO;

import java.util.List;

public interface HistorialService {

    HistorialResponseDTO crearHistorial(HistorialRequestDTO requestDTO);

    List<HistorialResponseDTO> listarHistoriales();

    HistorialResponseDTO buscarHistorialPorId(Long id);

    List<HistorialResponseDTO> buscarHistorialesPorPaciente(Long pacienteId);

    List<HistorialResponseDTO> buscarHistorialesPorDoctor(Long doctorId);

    List<HistorialResponseDTO> buscarHistorialesPorReserva(Long reservaId);

    HistorialResponseDTO actualizarHistorial(Long id, HistorialRequestDTO requestDTO);

    void eliminarHistorial(Long id);
}
