package com.clinica.ms_historial.service;

import com.clinica.ms_historial.client.DoctorClient;
import com.clinica.ms_historial.client.PacienteClient;
import com.clinica.ms_historial.client.ReservaClient;

import com.clinica.ms_historial.dto.HistorialRequestDTO;
import com.clinica.ms_historial.dto.HistorialResponseDTO;
import com.clinica.ms_historial.exception.ResourceNotFoundException;
import com.clinica.ms_historial.model.Historial;
import com.clinica.ms_historial.repository.HistorialRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HistorialServiceImpl implements HistorialService {

    private static final Logger log = LoggerFactory.getLogger(HistorialServiceImpl.class);

    private final HistorialRepository repository;
    private final PacienteClient pacienteClient;
    private final DoctorClient doctorClient;
    private final ReservaClient reservaClient;

    public HistorialServiceImpl(HistorialRepository repository,
                                PacienteClient pacienteClient,
                                DoctorClient doctorClient,
                                ReservaClient reservaClient) {
        this.repository = repository;
        this.pacienteClient = pacienteClient;
        this.doctorClient = doctorClient;
        this.reservaClient = reservaClient;
    }

    @Override
    public HistorialResponseDTO crearHistorial(HistorialRequestDTO requestDTO) {
        log.info("Creando historial para paciente ID: {}, doctor ID: {}, reserva ID: {}",
                requestDTO.getPacienteId(), requestDTO.getDoctorId(), requestDTO.getReservaId());

        validarPacienteDoctorYReserva(
                requestDTO.getPacienteId(),
                requestDTO.getDoctorId(),
                requestDTO.getReservaId()
        );

        Historial historial = new Historial();
        historial.setPacienteId(requestDTO.getPacienteId());
        historial.setDoctorId(requestDTO.getDoctorId());
        historial.setReservaId(requestDTO.getReservaId());
        historial.setDiagnostico(requestDTO.getDiagnostico());
        historial.setTratamiento(requestDTO.getTratamiento());
        historial.setObservaciones(requestDTO.getObservaciones());
        historial.setFechaRegistro(LocalDateTime.now());
        historial.setActivo(true);

        Historial guardado = repository.save(historial);
        log.info("Historial creado correctamente con ID: {}", guardado.getId());

        return convertirAResponseDTO(guardado);
    }

    @Override
    public List<HistorialResponseDTO> listarHistoriales() {
        log.info("Listando historiales");
        return repository.findAll()
                .stream()
                .map(this::convertirAResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public HistorialResponseDTO buscarHistorialPorId(Long id) {
        log.info("Buscando historial por ID: {}", id);
        Historial historial = obtenerHistorialPorId(id);
        return convertirAResponseDTO(historial);
    }

    @Override
    public List<HistorialResponseDTO> buscarHistorialesPorPaciente(Long pacienteId) {
        log.info("Buscando historiales por paciente ID: {}", pacienteId);
        return repository.findByPacienteId(pacienteId)
                .stream()
                .map(this::convertirAResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<HistorialResponseDTO> buscarHistorialesPorDoctor(Long doctorId) {
        log.info("Buscando historiales por doctor ID: {}", doctorId);
        return repository.findByDoctorId(doctorId)
                .stream()
                .map(this::convertirAResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<HistorialResponseDTO> buscarHistorialesPorReserva(Long reservaId) {
        log.info("Buscando historiales por reserva ID: {}", reservaId);
        return repository.findByReservaId(reservaId)
                .stream()
                .map(this::convertirAResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public HistorialResponseDTO actualizarHistorial(Long id, HistorialRequestDTO requestDTO) {
        log.info("Actualizando historial con ID: {}", id);
        Historial historial = obtenerHistorialPorId(id);

        validarPacienteDoctorYReserva(
                requestDTO.getPacienteId(),
                requestDTO.getDoctorId(),
                requestDTO.getReservaId()
        );

        historial.setPacienteId(requestDTO.getPacienteId());
        historial.setDoctorId(requestDTO.getDoctorId());
        historial.setReservaId(requestDTO.getReservaId());
        historial.setDiagnostico(requestDTO.getDiagnostico());
        historial.setTratamiento(requestDTO.getTratamiento());
        historial.setObservaciones(requestDTO.getObservaciones());

        Historial actualizado = repository.save(historial);
        log.info("Historial actualizado correctamente con ID: {}", actualizado.getId());

        return convertirAResponseDTO(actualizado);
    }

    @Override
    public void eliminarHistorial(Long id) {
        log.info("Eliminando logicamente historial con ID: {}", id);
        Historial historial = obtenerHistorialPorId(id);
        historial.setActivo(false);
        repository.save(historial);
        log.info("Historial desactivado correctamente con ID: {}", id);
    }

    private void validarPacienteDoctorYReserva(Long pacienteId, Long doctorId, Long reservaId) {
        try {
            pacienteClient.buscarPacientePorId(pacienteId);
        } catch (Exception ex) {
            throw new IllegalArgumentException("No existe un paciente activo con ID: " + pacienteId);
        }

        try {
            doctorClient.buscarDoctorPorId(doctorId);
        } catch (Exception ex) {
            throw new IllegalArgumentException("No existe un doctor activo con ID: " + doctorId);
        }

        try {
            reservaClient.buscarReservaPorId(reservaId);
        } catch (Exception ex) {
            throw new IllegalArgumentException("No existe una reserva activa con ID: " + reservaId);
        }
    }

    private Historial obtenerHistorialPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Historial no encontrado con ID: " + id));
    }

    private HistorialResponseDTO convertirAResponseDTO(Historial historial) {
        return new HistorialResponseDTO(
                historial.getId(),
                historial.getPacienteId(),
                historial.getDoctorId(),
                historial.getReservaId(),
                historial.getDiagnostico(),
                historial.getTratamiento(),
                historial.getObservaciones(),
                historial.getFechaRegistro(),
                historial.getActivo()
        );
    }
}
