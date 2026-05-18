package com.clinica.security_service.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.clinica.security_service.dto.PermisoRequestDTO;
import com.clinica.security_service.dto.PermisoResponseDTO;
import com.clinica.security_service.dto.RolRequestDTO;
import com.clinica.security_service.dto.RolResponseDTO;
import com.clinica.security_service.exception.SecurityException;
import com.clinica.security_service.model.Permiso;
import com.clinica.security_service.model.Rol;
import com.clinica.security_service.repository.PermisoRepository;
import com.clinica.security_service.repository.RolRepository;

@Service
public class SecurityServiceImpl implements SecurityService {

    private static final Logger log = LoggerFactory.getLogger(SecurityServiceImpl.class);

    private final RolRepository rolRepository;
    private final PermisoRepository permisoRepository;

    public SecurityServiceImpl(RolRepository rolRepository, PermisoRepository permisoRepository) {
        this.rolRepository = rolRepository;
        this.permisoRepository = permisoRepository;
    }

    @Override
    public RolResponseDTO crearRol(RolRequestDTO requestDTO) {
        log.info("Creando rol: {}", requestDTO.getNombre());

        if (rolRepository.existsByNombre(requestDTO.getNombre())) {
            throw new IllegalArgumentException("Ya existe un rol con el nombre: " + requestDTO.getNombre());
        }

        Rol rol = new Rol();
        rol.setNombre(requestDTO.getNombre());
        rol.setDescripcion(requestDTO.getDescripcion());
        rol.setActivo(true);

        Rol guardado = rolRepository.save(rol);
        log.info("Rol creado correctamente con ID: {}", guardado.getId());

        return mapearRolAResponseDTO(guardado);
    }

    @Override
    public List<RolResponseDTO> listarRoles() {
        log.info("Listando roles activos");

        return rolRepository.findAll()
                .stream()
                .filter(rol -> Boolean.TRUE.equals(rol.getActivo()))
                .map(this::mapearRolAResponseDTO)
                .toList();
    }

    @Override
    public RolResponseDTO buscarRolPorId(Long id) {
        log.info("Buscando rol por ID: {}", id);
        return mapearRolAResponseDTO(obtenerRolPorId(id));
    }

    @Override
    public RolResponseDTO buscarRolPorNombre(String nombre) {
        log.info("Buscando rol por nombre: {}", nombre);

        Rol rol = rolRepository.findByNombre(nombre)
                .orElseThrow(() -> new SecurityException("Rol no encontrado con nombre: " + nombre));

        return mapearRolAResponseDTO(rol);
    }

    @Override
    public RolResponseDTO actualizarRol(Long id, RolRequestDTO requestDTO) {
        log.info("Actualizando rol con ID: {}", id);

        Rol rol = obtenerRolPorId(id);

        rolRepository.findByNombre(requestDTO.getNombre())
                .filter(rolExistente -> !rolExistente.getId().equals(id))
                .ifPresent(rolExistente -> {
                    throw new IllegalArgumentException("Ya existe otro rol con el nombre: " + requestDTO.getNombre());
                });

        rol.setNombre(requestDTO.getNombre());
        rol.setDescripcion(requestDTO.getDescripcion());

        Rol actualizado = rolRepository.save(rol);
        log.info("Rol actualizado correctamente con ID: {}", actualizado.getId());

        return mapearRolAResponseDTO(actualizado);
    }

    @Override
    public void eliminarRol(Long id) {
        log.warn("Eliminando logicamente rol con ID: {}", id);

        Rol rol = obtenerRolPorId(id);
        rol.setActivo(false);
        rolRepository.save(rol);

        log.info("Rol desactivado correctamente con ID: {}", id);
    }

    @Override
    public PermisoResponseDTO crearPermiso(PermisoRequestDTO requestDTO) {
        log.info("Creando permiso: {}", requestDTO.getNombre());

        if (permisoRepository.existsByNombre(requestDTO.getNombre())) {
            throw new IllegalArgumentException("Ya existe un permiso con el nombre: " + requestDTO.getNombre());
        }

        Permiso permiso = new Permiso();
        permiso.setNombre(requestDTO.getNombre());
        permiso.setDescripcion(requestDTO.getDescripcion());
        permiso.setActivo(true);

        Permiso guardado = permisoRepository.save(permiso);
        log.info("Permiso creado correctamente con ID: {}", guardado.getId());

        return mapearPermisoAResponseDTO(guardado);
    }

    @Override
    public List<PermisoResponseDTO> listarPermisos() {
        log.info("Listando permisos activos");

        return permisoRepository.findAll()
                .stream()
                .filter(permiso -> Boolean.TRUE.equals(permiso.getActivo()))
                .map(this::mapearPermisoAResponseDTO)
                .toList();
    }

    @Override
    public PermisoResponseDTO buscarPermisoPorId(Long id) {
        log.info("Buscando permiso por ID: {}", id);
        return mapearPermisoAResponseDTO(obtenerPermisoPorId(id));
    }

    @Override
    public PermisoResponseDTO buscarPermisoPorNombre(String nombre) {
        log.info("Buscando permiso por nombre: {}", nombre);

        Permiso permiso = permisoRepository.findByNombre(nombre)
                .orElseThrow(() -> new SecurityException("Permiso no encontrado con nombre: " + nombre));

        return mapearPermisoAResponseDTO(permiso);
    }

    @Override
    public PermisoResponseDTO actualizarPermiso(Long id, PermisoRequestDTO requestDTO) {
        log.info("Actualizando permiso con ID: {}", id);

        Permiso permiso = obtenerPermisoPorId(id);

        permisoRepository.findByNombre(requestDTO.getNombre())
                .filter(permisoExistente -> !permisoExistente.getId().equals(id))
                .ifPresent(permisoExistente -> {
                    throw new IllegalArgumentException("Ya existe otro permiso con el nombre: " + requestDTO.getNombre());
                });

        permiso.setNombre(requestDTO.getNombre());
        permiso.setDescripcion(requestDTO.getDescripcion());

        Permiso actualizado = permisoRepository.save(permiso);
        log.info("Permiso actualizado correctamente con ID: {}", actualizado.getId());

        return mapearPermisoAResponseDTO(actualizado);
    }

    @Override
    public void eliminarPermiso(Long id) {
        log.warn("Eliminando logicamente permiso con ID: {}", id);

        Permiso permiso = obtenerPermisoPorId(id);
        permiso.setActivo(false);
        permisoRepository.save(permiso);

        log.info("Permiso desactivado correctamente con ID: {}", id);
    }

    @Override
    public boolean validarRol(String nombre) {
        log.info("Validando existencia de rol activo: {}", nombre);
        return rolRepository.existsByNombreAndActivoTrue(nombre);
    }

    @Override
    public boolean validarPermiso(String nombre) {
        log.info("Validando existencia de permiso activo: {}", nombre);
        return permisoRepository.existsByNombreAndActivoTrue(nombre);
    }

    private Rol obtenerRolPorId(Long id) {
        return rolRepository.findById(id)
                .orElseThrow(() -> new SecurityException("Rol no encontrado con ID: " + id));
    }

    private Permiso obtenerPermisoPorId(Long id) {
        return permisoRepository.findById(id)
                .orElseThrow(() -> new SecurityException("Permiso no encontrado con ID: " + id));
    }

    private RolResponseDTO mapearRolAResponseDTO(Rol rol) {
        return new RolResponseDTO(
                rol.getId(),
                rol.getNombre(),
                rol.getDescripcion(),
                rol.getActivo()
        );
    }

    private PermisoResponseDTO mapearPermisoAResponseDTO(Permiso permiso) {
        return new PermisoResponseDTO(
                permiso.getId(),
                permiso.getNombre(),
                permiso.getDescripcion(),
                permiso.getActivo()
        );
    }
}
