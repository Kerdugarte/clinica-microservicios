package com.clinica.ms_reservas.service;

import com.clinica.ms_reservas.client.DoctorClient;
import com.clinica.ms_reservas.client.PacienteClient;
import com.clinica.ms_reservas.dto.ReservaRequestDTO;
import com.clinica.ms_reservas.dto.ReservaResponseDTO;
import com.clinica.ms_reservas.exception.ResourceNotFoundException;
import com.clinica.ms_reservas.model.Reserva;
import com.clinica.ms_reservas.repository.ReservaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReservaServiceImpl implements ReservaService {

    private static final Logger log = LoggerFactory.getLogger(ReservaServiceImpl.class);

    private final ReservaRepository repository;
    private final PacienteClient pacienteClient;
    private final DoctorClient doctorClient;

    public ReservaServiceImpl(ReservaRepository repository, PacienteClient pacienteClient, DoctorClient doctorClient) {
        this.repository = repository;
        this.pacienteClient = pacienteClient;
        this.doctorClient = doctorClient;
    }

    @Override
    public ReservaResponseDTO crearReserva(ReservaRequestDTO requestDTO) {
        log.info("Creando reserva para paciente ID: {} y doctor ID: {}", requestDTO.getPacienteId(), requestDTO.getDoctorId());

        validarPacienteYDoctor(requestDTO.getPacienteId(), requestDTO.getDoctorId());

        validarDisponibilidad(requestDTO);

        Reserva reserva = new Reserva();
        reserva.setPacienteId(requestDTO.getPacienteId());
        reserva.setDoctorId(requestDTO.getDoctorId());
        reserva.setFechaReserva(requestDTO.getFechaReserva());
        reserva.setHoraReserva(requestDTO.getHoraReserva());
        reserva.setMotivo(requestDTO.getMotivo());
        reserva.setEstado(requestDTO.getEstado() != null && !requestDTO.getEstado().isBlank() ? requestDTO.getEstado() : "PENDIENTE");
        reserva.setActivo(true);

        Reserva guardada = repository.save(reserva);
        log.info("Reserva creada correctamente con ID: {}", guardada.getId());

        return convertirAResponseDTO(guardada);
    }

    @Override
    public List<ReservaResponseDTO> listarReservas() {
        log.info("Listando reservas");
        return repository.findAll()
                .stream()
                .map(this::convertirAResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ReservaResponseDTO buscarReservaPorId(Long id) {
        log.info("Buscando reserva por ID: {}", id);
        Reserva reserva = obtenerReservaPorId(id);
        return convertirAResponseDTO(reserva);
    }

    @Override
    public List<ReservaResponseDTO> buscarReservasPorPaciente(Long pacienteId) {
        log.info("Buscando reservas por paciente ID: {}", pacienteId);
        return repository.findByPacienteId(pacienteId)
                .stream()
                .map(this::convertirAResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ReservaResponseDTO> buscarReservasPorDoctor(Long doctorId) {
        log.info("Buscando reservas por doctor ID: {}", doctorId);
        return repository.findByDoctorId(doctorId)
                .stream()
                .map(this::convertirAResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ReservaResponseDTO> buscarReservasPorEstado(String estado) {
        log.info("Buscando reservas por estado: {}", estado);
        return repository.findByEstado(estado)
                .stream()
                .map(this::convertirAResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ReservaResponseDTO actualizarReserva(Long id, ReservaRequestDTO requestDTO) {
        log.info("Actualizando reserva con ID: {}", id);
        Reserva reserva = obtenerReservaPorId(id);

        validarPacienteYDoctor(requestDTO.getPacienteId(), requestDTO.getDoctorId());

        boolean cambioHorarioDoctor = !reserva.getDoctorId().equals(requestDTO.getDoctorId())
                || !reserva.getFechaReserva().equals(requestDTO.getFechaReserva())
                || !reserva.getHoraReserva().equals(requestDTO.getHoraReserva());

        boolean cambioHorarioPaciente = !reserva.getPacienteId().equals(requestDTO.getPacienteId())
                || !reserva.getFechaReserva().equals(requestDTO.getFechaReserva())
                || !reserva.getHoraReserva().equals(requestDTO.getHoraReserva());

        if (cambioHorarioDoctor && repository.existsByDoctorIdAndFechaReservaAndHoraReservaAndActivoTrue(
                requestDTO.getDoctorId(), requestDTO.getFechaReserva(), requestDTO.getHoraReserva())) {
            throw new IllegalArgumentException("El doctor ya tiene una reserva activa en esa fecha y hora");
        }

        if (cambioHorarioPaciente && repository.existsByPacienteIdAndFechaReservaAndHoraReservaAndActivoTrue(
                requestDTO.getPacienteId(), requestDTO.getFechaReserva(), requestDTO.getHoraReserva())) {
            throw new IllegalArgumentException("El paciente ya tiene una reserva activa en esa fecha y hora");
        }

        reserva.setPacienteId(requestDTO.getPacienteId());
        reserva.setDoctorId(requestDTO.getDoctorId());
        reserva.setFechaReserva(requestDTO.getFechaReserva());
        reserva.setHoraReserva(requestDTO.getHoraReserva());
        reserva.setMotivo(requestDTO.getMotivo());

        if (requestDTO.getEstado() != null && !requestDTO.getEstado().isBlank()) {
            reserva.setEstado(requestDTO.getEstado());
        }

        Reserva actualizada = repository.save(reserva);
        log.info("Reserva actualizada correctamente con ID: {}", actualizada.getId());

        return convertirAResponseDTO(actualizada);
    }

    @Override
    public ReservaResponseDTO cambiarEstadoReserva(Long id, String estado) {
        log.info("Cambiando estado de reserva ID: {} a {}", id, estado);
        Reserva reserva = obtenerReservaPorId(id);
        reserva.setEstado(estado);
        Reserva actualizada = repository.save(reserva);
        return convertirAResponseDTO(actualizada);
    }

    @Override
    public void eliminarReserva(Long id) {
        log.info("Eliminando logicamente reserva con ID: {}", id);
        Reserva reserva = obtenerReservaPorId(id);
        reserva.setActivo(false);
        repository.save(reserva);
        log.info("Reserva desactivada correctamente con ID: {}", id);
    }

    private void validarPacienteYDoctor(Long pacienteId, Long doctorId) {
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
    }

    private void validarDisponibilidad(ReservaRequestDTO requestDTO) {
        if (repository.existsByDoctorIdAndFechaReservaAndHoraReservaAndActivoTrue(
                requestDTO.getDoctorId(), requestDTO.getFechaReserva(), requestDTO.getHoraReserva())) {
            throw new IllegalArgumentException("El doctor ya tiene una reserva activa en esa fecha y hora");
        }

        if (repository.existsByPacienteIdAndFechaReservaAndHoraReservaAndActivoTrue(
                requestDTO.getPacienteId(), requestDTO.getFechaReserva(), requestDTO.getHoraReserva())) {
            throw new IllegalArgumentException("El paciente ya tiene una reserva activa en esa fecha y hora");
        }
    }

    private Reserva obtenerReservaPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reserva no encontrada con ID: " + id));
    }

    private ReservaResponseDTO convertirAResponseDTO(Reserva reserva) {
        return new ReservaResponseDTO(
                reserva.getId(),
                reserva.getPacienteId(),
                reserva.getDoctorId(),
                reserva.getFechaReserva(),
                reserva.getHoraReserva(),
                reserva.getMotivo(),
                reserva.getEstado(),
                reserva.getActivo()
        );
    }
}
