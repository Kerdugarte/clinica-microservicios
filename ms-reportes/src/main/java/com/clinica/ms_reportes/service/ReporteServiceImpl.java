package com.clinica.ms_reportes.service;

import com.clinica.ms_reportes.client.DoctorClient;
import com.clinica.ms_reportes.client.HistorialClient;
import com.clinica.ms_reportes.client.PacienteClient;
import com.clinica.ms_reportes.client.PagoClient;
import com.clinica.ms_reportes.client.ReservaClient;
import com.clinica.ms_reportes.dto.ReporteDetalleDTO;
import com.clinica.ms_reportes.dto.ReporteResumenDTO;
import com.clinica.ms_reportes.model.ReporteGenerado;
import com.clinica.ms_reportes.repository.ReporteGeneradoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class ReporteServiceImpl implements ReporteService {

    private static final Logger log = LoggerFactory.getLogger(ReporteServiceImpl.class);

    private final PacienteClient pacienteClient;
    private final DoctorClient doctorClient;
    private final ReservaClient reservaClient;
    private final PagoClient pagoClient;
    private final HistorialClient historialClient;
    private final ReporteGeneradoRepository reporteGeneradoRepository;

    public ReporteServiceImpl(PacienteClient pacienteClient,
                              DoctorClient doctorClient,
                              ReservaClient reservaClient,
                              PagoClient pagoClient,
                              HistorialClient historialClient,
                              ReporteGeneradoRepository reporteGeneradoRepository) {
        this.pacienteClient = pacienteClient;
        this.doctorClient = doctorClient;
        this.reservaClient = reservaClient;
        this.pagoClient = pagoClient;
        this.historialClient = historialClient;
        this.reporteGeneradoRepository = reporteGeneradoRepository;
    }

    @Override
    public ReporteResumenDTO obtenerResumenGeneral() {
        log.info("Generando resumen general de la clinica");

        List<Object> pacientes = pacienteClient.listarPacientes();
        List<Object> doctores = doctorClient.listarDoctores();
        List<Object> reservas = reservaClient.listarReservas();
        List<Object> pagos = pagoClient.listarPagos();
        List<Object> historiales = historialClient.listarHistoriales();

        registrarReporteGenerado("RESUMEN_GENERAL", null, "Resumen general de la clinica generado correctamente");

        return new ReporteResumenDTO(
                "Resumen general de la clinica",
                "Reporte general generado desde ms-reportes usando Feign Clients",
                pacientes.size(),
                doctores.size(),
                reservas.size(),
                pagos.size(),
                historiales.size()
        );
    }

    @Override
    public ReporteDetalleDTO obtenerReportePaciente(Long pacienteId) {
        log.info("Generando reporte para paciente ID: {}", pacienteId);

        Object paciente = pacienteClient.buscarPacientePorId(pacienteId);
        List<Object> historiales = historialClient.buscarHistorialesPorPaciente(pacienteId);

        Map<String, Object> relacionados = new HashMap<>();
        relacionados.put("historiales", historiales);

        registrarReporteGenerado("PACIENTE", pacienteId, "Reporte de paciente generado correctamente");

        return new ReporteDetalleDTO(
                "Reporte de paciente",
                "PACIENTE",
                pacienteId,
                paciente,
                relacionados,
                "Reporte generado correctamente"
        );
    }

    @Override
    public ReporteDetalleDTO obtenerReporteDoctor(Long doctorId) {
        log.info("Generando reporte para doctor ID: {}", doctorId);

        Object doctor = doctorClient.buscarDoctorPorId(doctorId);
        List<Object> historiales = historialClient.buscarHistorialesPorDoctor(doctorId);

        Map<String, Object> relacionados = new HashMap<>();
        relacionados.put("historiales", historiales);

        registrarReporteGenerado("DOCTOR", doctorId, "Reporte de doctor generado correctamente");

        return new ReporteDetalleDTO(
                "Reporte de doctor",
                "DOCTOR",
                doctorId,
                doctor,
                relacionados,
                "Reporte generado correctamente"
        );
    }

    @Override
    public ReporteDetalleDTO obtenerReporteReserva(Long reservaId) {
        log.info("Generando reporte para reserva ID: {}", reservaId);

        Object reserva = reservaClient.buscarReservaPorId(reservaId);
        List<Object> pagos = pagoClient.buscarPagosPorReserva(reservaId);
        List<Object> historiales = historialClient.buscarHistorialesPorReserva(reservaId);

        Map<String, Object> relacionados = new HashMap<>();
        relacionados.put("pagos", pagos);
        relacionados.put("historiales", historiales);

        registrarReporteGenerado("RESERVA", reservaId, "Reporte de reserva generado correctamente");

        return new ReporteDetalleDTO(
                "Reporte de reserva",
                "RESERVA",
                reservaId,
                reserva,
                relacionados,
                "Reporte generado correctamente"
        );
    }

    private void registrarReporteGenerado(String tipoReporte, Long referenciaId, String descripcion) {
        ReporteGenerado reporteGenerado = new ReporteGenerado();
        reporteGenerado.setTipoReporte(tipoReporte);
        reporteGenerado.setReferenciaId(referenciaId);
        reporteGenerado.setDescripcion(descripcion);
        reporteGenerado.setFechaGeneracion(LocalDateTime.now());
        reporteGenerado.setActivo(true);

        reporteGeneradoRepository.save(reporteGenerado);
        log.info("Reporte registrado en base de datos. Tipo: {}, referenciaId: {}", tipoReporte, referenciaId);
    }
}
