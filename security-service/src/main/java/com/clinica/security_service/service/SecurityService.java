package com.clinica.security_service.service;

import com.clinica.security_service.dto.PermisoRequestDTO;
import com.clinica.security_service.dto.PermisoResponseDTO;
import com.clinica.security_service.dto.RolRequestDTO;
import com.clinica.security_service.dto.RolResponseDTO;

import java.util.List;

public interface SecurityService {

    RolResponseDTO crearRol(RolRequestDTO requestDTO);

    List<RolResponseDTO> listarRoles();

    RolResponseDTO buscarRolPorId(Long id);

    RolResponseDTO buscarRolPorNombre(String nombre);

    RolResponseDTO actualizarRol(Long id, RolRequestDTO requestDTO);

    void eliminarRol(Long id);

    PermisoResponseDTO crearPermiso(PermisoRequestDTO requestDTO);

    List<PermisoResponseDTO> listarPermisos();

    PermisoResponseDTO buscarPermisoPorId(Long id);

    PermisoResponseDTO buscarPermisoPorNombre(String nombre);

    PermisoResponseDTO actualizarPermiso(Long id, PermisoRequestDTO requestDTO);

    void eliminarPermiso(Long id);

    boolean validarRol(String nombre);

    boolean validarPermiso(String nombre);
}
