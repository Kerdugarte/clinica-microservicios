package com.clinica.ms_pacientes.service;

import com.clinica.ms_pacientes.dto.PacienteRequestDTO;
import com.clinica.ms_pacientes.dto.PacienteResponseDTO;

import java.util.List;

public interface PacienteService {

    PacienteResponseDTO crearPaciente(PacienteRequestDTO requestDTO);

    List<PacienteResponseDTO> listarPacientes();

    PacienteResponseDTO buscarPacientePorId(Long id);

    PacienteResponseDTO buscarPacientePorRut(String rut);

    PacienteResponseDTO actualizarPaciente(Long id, PacienteRequestDTO requestDTO);

    void eliminarPaciente(Long id);

    boolean validarPacientePorRut(String rut);

    boolean validarPacientePorEmail(String email);
}
