package com.clinica.auth_service.service;

import com.clinica.auth_service.dto.LoginRequestDTO;
import com.clinica.auth_service.dto.LoginResponseDTO;
import com.clinica.auth_service.dto.RegistroRequestDTO;

public interface AuthService {

    LoginResponseDTO registrar(RegistroRequestDTO requestDTO);

    LoginResponseDTO login(LoginRequestDTO requestDTO);

    boolean validarCredencial(String email);
}
