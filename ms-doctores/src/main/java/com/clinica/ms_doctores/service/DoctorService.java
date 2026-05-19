package com.clinica.ms_doctores.service;

import com.clinica.ms_doctores.dto.DoctorRequestDTO;
import com.clinica.ms_doctores.dto.DoctorResponseDTO;

import java.util.List;

public interface DoctorService {

    DoctorResponseDTO crearDoctor(DoctorRequestDTO requestDTO);

    List<DoctorResponseDTO> listarDoctores();

    DoctorResponseDTO buscarDoctorPorId(Long id);

    DoctorResponseDTO buscarDoctorPorRut(String rut);

    DoctorResponseDTO buscarDoctorPorNumeroRegistro(String numeroRegistro);

    DoctorResponseDTO actualizarDoctor(Long id, DoctorRequestDTO requestDTO);

    void eliminarDoctor(Long id);

    boolean validarDoctorPorRut(String rut);

    boolean validarDoctorPorEmail(String email);

    boolean validarDoctorPorNumeroRegistro(String numeroRegistro);
}
