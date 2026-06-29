package com.clinica.ms_doctores;

import com.clinica.ms_doctores.dto.DoctorRequestDTO;
import com.clinica.ms_doctores.dto.DoctorResponseDTO;
import com.clinica.ms_doctores.model.Doctor;
import com.clinica.ms_doctores.repository.DoctorRepository;
import com.clinica.ms_doctores.service.DoctorServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class MsDoctoresApplicationTests {

    @Mock
    private DoctorRepository doctorRepository;

    @InjectMocks
    private DoctorServiceImpl doctorService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void crearDoctor_deberiaGuardarDoctorCorrectamente() {
        // Given
        DoctorRequestDTO request = new DoctorRequestDTO();
        request.setNombre("Maria");
        request.setApellido("Gonzalez");
        request.setRut("11111111-1");
        request.setEmail("maria@clinica.cl");
        request.setTelefono("912345678");
        request.setEspecialidad("Medicina General");
        request.setRegistroMedico("RM-001");

        Doctor doctorGuardado = new Doctor();
        doctorGuardado.setId(1L);
        doctorGuardado.setNombre("Maria");
        doctorGuardado.setApellido("Gonzalez");
        doctorGuardado.setRut("11111111-1");
        doctorGuardado.setEmail("maria@clinica.cl");
        doctorGuardado.setTelefono("912345678");
        doctorGuardado.setEspecialidad("Medicina General");
        doctorGuardado.setRegistroMedico("RM-001");
        doctorGuardado.setActivo(true);

        when(doctorRepository.existsByRut("11111111-1")).thenReturn(false);
        when(doctorRepository.existsByEmail("maria@clinica.cl")).thenReturn(false);
        when(doctorRepository.existsByRegistroMedico("RM-001")).thenReturn(false);
        when(doctorRepository.save(any(Doctor.class))).thenReturn(doctorGuardado);

        // When
        DoctorResponseDTO response = doctorService.crearDoctor(request);

        // Then
        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("Maria", response.getNombre());
        assertEquals("Gonzalez", response.getApellido());
        assertEquals("11111111-1", response.getRut());
        assertEquals("maria@clinica.cl", response.getEmail());
        assertEquals("Medicina General", response.getEspecialidad());
        assertEquals("RM-001", response.getRegistroMedico());

        verify(doctorRepository, times(1)).save(any(Doctor.class));
    }

    @Test
    void buscarDoctorPorId_deberiaRetornarDoctorCuandoExiste() {
        // Given
        Doctor doctor = new Doctor();
        doctor.setId(1L);
        doctor.setNombre("Maria");
        doctor.setApellido("Gonzalez");
        doctor.setRut("11111111-1");
        doctor.setEmail("maria@clinica.cl");
        doctor.setTelefono("912345678");
        doctor.setEspecialidad("Medicina General");
        doctor.setRegistroMedico("RM-001");
        doctor.setActivo(true);

        when(doctorRepository.findById(1L)).thenReturn(Optional.of(doctor));

        // When
        DoctorResponseDTO response = doctorService.buscarDoctorPorId(1L);

        // Then
        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("Maria", response.getNombre());
        assertEquals("11111111-1", response.getRut());

        verify(doctorRepository, times(1)).findById(1L);
    }

    @Test
    void eliminarDoctor_deberiaDesactivarDoctor() {
        // Given
        Doctor doctor = new Doctor();
        doctor.setId(1L);
        doctor.setNombre("Maria");
        doctor.setApellido("Gonzalez");
        doctor.setRut("11111111-1");
        doctor.setEmail("maria@clinica.cl");
        doctor.setTelefono("912345678");
        doctor.setEspecialidad("Medicina General");
        doctor.setRegistroMedico("RM-001");
        doctor.setActivo(true);

        when(doctorRepository.findById(1L)).thenReturn(Optional.of(doctor));
        when(doctorRepository.save(any(Doctor.class))).thenReturn(doctor);

        // When
        doctorService.eliminarDoctor(1L);

        // Then
        assertFalse(doctor.getActivo());
        verify(doctorRepository, times(1)).findById(1L);
        verify(doctorRepository, times(1)).save(doctor);
    }
}
