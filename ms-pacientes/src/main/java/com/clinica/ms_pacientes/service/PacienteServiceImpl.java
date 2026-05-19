package com.clinica.ms_pacientes.service;

import com.clinica.ms_pacientes.dto.PacienteRequestDTO;
import com.clinica.ms_pacientes.dto.PacienteResponseDTO;
import com.clinica.ms_pacientes.exception.ResourceNotFoundException;
import com.clinica.ms_pacientes.model.Paciente;
import com.clinica.ms_pacientes.repository.PacienteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PacienteServiceImpl implements PacienteService {

    private static final Logger log = LoggerFactory.getLogger(PacienteServiceImpl.class);

    private final PacienteRepository repository;

    public PacienteServiceImpl(PacienteRepository repository) {
        this.repository = repository;
    }

    @Override
    public PacienteResponseDTO crearPaciente(PacienteRequestDTO requestDTO) {
        log.info("Creando paciente con RUT: {}", requestDTO.getRut());

        if (repository.existsByRut(requestDTO.getRut())) {
            throw new IllegalArgumentException("Ya existe un paciente registrado con ese RUT");
        }

        if (repository.existsByEmail(requestDTO.getEmail())) {
            throw new IllegalArgumentException("Ya existe un paciente registrado con ese email");
        }

        Paciente paciente = new Paciente();
        paciente.setNombre(requestDTO.getNombre());
        paciente.setApellido(requestDTO.getApellido());
        paciente.setRut(requestDTO.getRut());
        paciente.setEmail(requestDTO.getEmail());
        paciente.setTelefono(requestDTO.getTelefono());
        paciente.setFechaNacimiento(requestDTO.getFechaNacimiento());
        paciente.setDireccion(requestDTO.getDireccion());
        paciente.setActivo(true);

        Paciente guardado = repository.save(paciente);
        log.info("Paciente creado correctamente con ID: {}", guardado.getId());

        return convertirAResponseDTO(guardado);
    }

    @Override
    public List<PacienteResponseDTO> listarPacientes() {
        log.info("Listando pacientes");
        return repository.findAll()
                .stream()
                .map(this::convertirAResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public PacienteResponseDTO buscarPacientePorId(Long id) {
        log.info("Buscando paciente por ID: {}", id);
        Paciente paciente = obtenerPacientePorId(id);
        return convertirAResponseDTO(paciente);
    }

    @Override
    public PacienteResponseDTO buscarPacientePorRut(String rut) {
        log.info("Buscando paciente por RUT: {}", rut);
        Paciente paciente = repository.findByRut(rut)
                .orElseThrow(() -> new ResourceNotFoundException("Paciente no encontrado con RUT: " + rut));
        return convertirAResponseDTO(paciente);
    }

    @Override
    public PacienteResponseDTO actualizarPaciente(Long id, PacienteRequestDTO requestDTO) {
        log.info("Actualizando paciente con ID: {}", id);
        Paciente paciente = obtenerPacientePorId(id);

        repository.findByRut(requestDTO.getRut()).ifPresent(pacienteExistente -> {
            if (!pacienteExistente.getId().equals(id)) {
                throw new IllegalArgumentException("Ya existe otro paciente registrado con ese RUT");
            }
        });

        repository.findByEmail(requestDTO.getEmail()).ifPresent(pacienteExistente -> {
            if (!pacienteExistente.getId().equals(id)) {
                throw new IllegalArgumentException("Ya existe otro paciente registrado con ese email");
            }
        });

        paciente.setNombre(requestDTO.getNombre());
        paciente.setApellido(requestDTO.getApellido());
        paciente.setRut(requestDTO.getRut());
        paciente.setEmail(requestDTO.getEmail());
        paciente.setTelefono(requestDTO.getTelefono());
        paciente.setFechaNacimiento(requestDTO.getFechaNacimiento());
        paciente.setDireccion(requestDTO.getDireccion());

        Paciente actualizado = repository.save(paciente);
        log.info("Paciente actualizado correctamente con ID: {}", actualizado.getId());

        return convertirAResponseDTO(actualizado);
    }

    @Override
    public void eliminarPaciente(Long id) {
        log.info("Eliminando logicamente paciente con ID: {}", id);
        Paciente paciente = obtenerPacientePorId(id);
        paciente.setActivo(false);
        repository.save(paciente);
        log.info("Paciente desactivado correctamente con ID: {}", id);
    }

    @Override
    public boolean validarPacientePorRut(String rut) {
        log.info("Validando existencia de paciente activo por RUT: {}", rut);
        return repository.existsByRutAndActivoTrue(rut);
    }

    @Override
    public boolean validarPacientePorEmail(String email) {
        log.info("Validando existencia de paciente activo por email: {}", email);
        return repository.existsByEmailAndActivoTrue(email);
    }

    private Paciente obtenerPacientePorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Paciente no encontrado con ID: " + id));
    }

    private PacienteResponseDTO convertirAResponseDTO(Paciente paciente) {
        return new PacienteResponseDTO(
                paciente.getId(),
                paciente.getNombre(),
                paciente.getApellido(),
                paciente.getRut(),
                paciente.getEmail(),
                paciente.getTelefono(),
                paciente.getFechaNacimiento(),
                paciente.getDireccion(),
                paciente.getActivo()
        );
    }
}
