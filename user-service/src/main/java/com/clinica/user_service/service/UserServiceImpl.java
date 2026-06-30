package com.clinica.user_service.service;

import com.clinica.user_service.dto.UserRequestDTO;
import com.clinica.user_service.dto.UserResponseDTO;
import com.clinica.user_service.exception.UserNotFoundException;
import com.clinica.user_service.model.User;
import com.clinica.user_service.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepository repository;

    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserResponseDTO crearUsuario(UserRequestDTO requestDTO) {
        log.info("Creando usuario con email: {}", requestDTO.getEmail());

        if (repository.existsByEmail(requestDTO.getEmail())) {
            throw new IllegalArgumentException("Ya existe un usuario con el email: " + requestDTO.getEmail());
        }

        User user = new User();
        user.setNombre(requestDTO.getNombre());
        user.setApellido(requestDTO.getApellido());
        user.setEmail(requestDTO.getEmail());
        user.setRol(requestDTO.getRol());
        user.setActivo(true);

        User guardado = repository.save(user);

        log.info("Usuario creado correctamente con ID: {}", guardado.getId());
        return mapearAResponseDTO(guardado);
    }

    @Override
    public List<UserResponseDTO> listarUsuarios() {
        log.info("Listando usuarios activos");

        return repository.findAll()
                .stream()
                .filter(user -> Boolean.TRUE.equals(user.getActivo()))
                .map(this::mapearAResponseDTO)
                .toList();
    }

    @Override
    public UserResponseDTO buscarPorId(Long id) {
        log.info("Buscando usuario por ID: {}", id);

        User user = obtenerEntidadPorId(id);
        return mapearAResponseDTO(user);
    }

    @Override
    public UserResponseDTO buscarPorEmail(String email) {
        log.info("Buscando usuario por email: {}", email);

        User user = repository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("Usuario no encontrado con email: " + email));

        return mapearAResponseDTO(user);
    }

    @Override
    public UserResponseDTO actualizarUsuario(Long id, UserRequestDTO requestDTO) {
        log.info("Actualizando usuario con ID: {}", id);

        User user = obtenerEntidadPorId(id);

        repository.findByEmail(requestDTO.getEmail())
                .filter(usuarioExistente -> !usuarioExistente.getId().equals(id))
                .ifPresent(usuarioExistente -> {
                    throw new IllegalArgumentException("Ya existe otro usuario con el email: " + requestDTO.getEmail());
                });

        user.setNombre(requestDTO.getNombre());
        user.setApellido(requestDTO.getApellido());
        user.setEmail(requestDTO.getEmail());
        user.setRol(requestDTO.getRol());

        User actualizado = repository.save(user);

        log.info("Usuario actualizado correctamente con ID: {}", actualizado.getId());
        return mapearAResponseDTO(actualizado);
    }

    @Override
    public void eliminarUsuario(Long id) {
        log.warn("Eliminando logicamente usuario con ID: {}", id);

        User user = obtenerEntidadPorId(id);
        user.setActivo(false);

        repository.save(user);
        log.info("Usuario desactivado correctamente con ID: {}", id);
    }

    @Override
    public boolean existeUsuario(Long id) {
        log.info("Validando existencia de usuario con ID: {}", id);
        return repository.existsById(id);
    }

    private User obtenerEntidadPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Usuario no encontrado con ID: " + id));
    }

    private UserResponseDTO mapearAResponseDTO(User user) {
        return new UserResponseDTO(
                user.getId(),
                user.getNombre(),
                user.getApellido(),
                user.getEmail(),
                user.getRol(),
                user.getActivo()
        );
    }
}
