package com.clinica.user_service.service;

import com.clinica.user_service.dto.UserRequestDTO;
import com.clinica.user_service.dto.UserResponseDTO;

import java.util.List;

public interface UserService {

    UserResponseDTO crearUsuario(UserRequestDTO requestDTO);

    List<UserResponseDTO> listarUsuarios();

    UserResponseDTO buscarPorId(Long id);

    UserResponseDTO buscarPorEmail(String email);

    UserResponseDTO actualizarUsuario(Long id, UserRequestDTO requestDTO);

    void eliminarUsuario(Long id);

    boolean existeUsuario(Long id);
}
