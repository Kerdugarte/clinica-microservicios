package com.clinica.ms_doctores.service;

import com.clinica.ms_doctores.dto.DoctorRequestDTO;
import com.clinica.ms_doctores.dto.DoctorResponseDTO;
import com.clinica.ms_doctores.exception.ResourceNotFoundException;
import com.clinica.ms_doctores.model.Doctor;
import com.clinica.ms_doctores.repository.DoctorRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DoctorServiceImpl implements DoctorService {

    private static final Logger log = LoggerFactory.getLogger(DoctorServiceImpl.class);

    private final DoctorRepository repository;

    public DoctorServiceImpl(DoctorRepository repository) {
        this.repository = repository;
    }

    @Override
    public DoctorResponseDTO crearDoctor(DoctorRequestDTO requestDTO) {
        log.info("Creando doctor con RUT: {}", requestDTO.getRut());

        if (repository.existsByRut(requestDTO.getRut())) {
            throw new IllegalArgumentException("Ya existe un doctor registrado con ese RUT");
        }

        if (repository.existsByEmail(requestDTO.getEmail())) {
            throw new IllegalArgumentException("Ya existe un doctor registrado con ese email");
        }

        if (repository.existsByNumeroRegistro(requestDTO.getNumeroRegistro())) {
            throw new IllegalArgumentException("Ya existe un doctor registrado con ese numero de registro");
        }

        Doctor doctor = new Doctor();
        doctor.setNombre(requestDTO.getNombre());
        doctor.setApellido(requestDTO.getApellido());
        doctor.setRut(requestDTO.getRut());
        doctor.setEmail(requestDTO.getEmail());
        doctor.setTelefono(requestDTO.getTelefono());
        doctor.setEspecialidad(requestDTO.getEspecialidad());
        doctor.setNumeroRegistro(requestDTO.getNumeroRegistro());
        doctor.setActivo(true);

        Doctor guardado = repository.save(doctor);
        log.info("Doctor creado correctamente con ID: {}", guardado.getId());

        return convertirAResponseDTO(guardado);
    }

    @Override
    public List<DoctorResponseDTO> listarDoctores() {
        log.info("Listando doctores");
        return repository.findAll()
                .stream()
                .map(this::convertirAResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public DoctorResponseDTO buscarDoctorPorId(Long id) {
        log.info("Buscando doctor por ID: {}", id);
        Doctor doctor = obtenerDoctorPorId(id);
        return convertirAResponseDTO(doctor);
    }

    @Override
    public DoctorResponseDTO buscarDoctorPorRut(String rut) {
        log.info("Buscando doctor por RUT: {}", rut);
        Doctor doctor = repository.findByRut(rut)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor no encontrado con RUT: " + rut));
        return convertirAResponseDTO(doctor);
    }

    @Override
    public DoctorResponseDTO buscarDoctorPorNumeroRegistro(String numeroRegistro) {
        log.info("Buscando doctor por numero de registro: {}", numeroRegistro);
        Doctor doctor = repository.findByNumeroRegistro(numeroRegistro)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor no encontrado con numero de registro: " + numeroRegistro));
        return convertirAResponseDTO(doctor);
    }

    @Override
    public DoctorResponseDTO actualizarDoctor(Long id, DoctorRequestDTO requestDTO) {
        log.info("Actualizando doctor con ID: {}", id);
        Doctor doctor = obtenerDoctorPorId(id);

        repository.findByRut(requestDTO.getRut()).ifPresent(doctorExistente -> {
            if (!doctorExistente.getId().equals(id)) {
                throw new IllegalArgumentException("Ya existe otro doctor registrado con ese RUT");
            }
        });

        repository.findByEmail(requestDTO.getEmail()).ifPresent(doctorExistente -> {
            if (!doctorExistente.getId().equals(id)) {
                throw new IllegalArgumentException("Ya existe otro doctor registrado con ese email");
            }
        });

        repository.findByNumeroRegistro(requestDTO.getNumeroRegistro()).ifPresent(doctorExistente -> {
            if (!doctorExistente.getId().equals(id)) {
                throw new IllegalArgumentException("Ya existe otro doctor registrado con ese numero de registro");
            }
        });

        doctor.setNombre(requestDTO.getNombre());
        doctor.setApellido(requestDTO.getApellido());
        doctor.setRut(requestDTO.getRut());
        doctor.setEmail(requestDTO.getEmail());
        doctor.setTelefono(requestDTO.getTelefono());
        doctor.setEspecialidad(requestDTO.getEspecialidad());
        doctor.setNumeroRegistro(requestDTO.getNumeroRegistro());

        Doctor actualizado = repository.save(doctor);
        log.info("Doctor actualizado correctamente con ID: {}", actualizado.getId());

        return convertirAResponseDTO(actualizado);
    }

    @Override
    public void eliminarDoctor(Long id) {
        log.info("Eliminando logicamente doctor con ID: {}", id);
        Doctor doctor = obtenerDoctorPorId(id);
        doctor.setActivo(false);
        repository.save(doctor);
        log.info("Doctor desactivado correctamente con ID: {}", id);
    }

    @Override
    public boolean validarDoctorPorRut(String rut) {
        log.info("Validando existencia de doctor activo por RUT: {}", rut);
        return repository.existsByRutAndActivoTrue(rut);
    }

    @Override
    public boolean validarDoctorPorEmail(String email) {
        log.info("Validando existencia de doctor activo por email: {}", email);
        return repository.existsByEmailAndActivoTrue(email);
    }

    @Override
    public boolean validarDoctorPorNumeroRegistro(String numeroRegistro) {
        log.info("Validando existencia de doctor activo por numero de registro: {}", numeroRegistro);
        return repository.existsByNumeroRegistroAndActivoTrue(numeroRegistro);
    }

    private Doctor obtenerDoctorPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor no encontrado con ID: " + id));
    }

    private DoctorResponseDTO convertirAResponseDTO(Doctor doctor) {
        return new DoctorResponseDTO(
                doctor.getId(),
                doctor.getNombre(),
                doctor.getApellido(),
                doctor.getRut(),
                doctor.getEmail(),
                doctor.getTelefono(),
                doctor.getEspecialidad(),
                doctor.getNumeroRegistro(),
                doctor.getActivo()
        );
    }
}
