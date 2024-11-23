package com.example.microserviciofichasmedicas.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.microserviciofichasmedicas.model.ConsultaMedicaEntity;
import com.example.microserviciofichasmedicas.model.ConsultorioEntity;
import com.example.microserviciofichasmedicas.model.EspecialidadesEntity;
import com.example.microserviciofichasmedicas.model.TurnoEntity;
import com.example.microserviciofichasmedicas.model.TurnosAtencionMedicaEntity;
import com.example.microserviciofichasmedicas.model.UsuarioEntity;
import com.example.microserviciofichasmedicas.model.dtos.ConsultaMedicaDto;
import com.example.microserviciofichasmedicas.repository.ConsultaMedicaRepositoryJPA;
import com.example.microserviciofichasmedicas.repository.TurnosAtencionMedicaRepositoryJPA;
import com.example.microserviciofichasmedicas.repository.UsuarioRepositoryJPA;
import com.example.microserviciofichasmedicas.service.ConsultasMedicasService;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ConsultasMedicasServiceTest {
    
    @InjectMocks
    private ConsultasMedicasService consultasMedicasService;

    @Mock
    private UsuarioRepositoryJPA usuarioRepositoryJPA;

    @Mock
    private ConsultaMedicaRepositoryJPA consultaMedicaRepositoryJPA;

    @Mock
    private TurnosAtencionMedicaRepositoryJPA turnosAtencionMedicaRepositoryJPA;


    TurnosAtencionMedicaEntity turnosAtencionMedicaEntity=new TurnosAtencionMedicaEntity();
    TurnoEntity turnoEntity=new TurnoEntity();
    UsuarioEntity medicoEntity=new UsuarioEntity();
    UsuarioEntity pacienteEntity=new UsuarioEntity();
    ConsultorioEntity consultorioEntity=new ConsultorioEntity();
    EspecialidadesEntity especialidadesEntity=new EspecialidadesEntity();
    
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

    @BeforeAll
    void beforeAll() throws ParseException{
        especialidadesEntity.setIdEspecialidad(1);
        especialidadesEntity.setNombre("Pediatría");

        consultorioEntity.setIdConsultorio(1);
        consultorioEntity.setNombre("Consultorio A");
        consultorioEntity.setEspecialidad(especialidadesEntity);

        turnoEntity.setIdTurno(1);
        turnoEntity.setNombre("Mañana");
        turnoEntity.setHoraInicio(LocalTime.of(8, 0));
        turnoEntity.setHoraFin(LocalTime.of(10, 0));

        medicoEntity.setIdUsuario(1);
        medicoEntity.setNombres("Juan");
        medicoEntity.setApellidoPaterno("Pérez");
        medicoEntity.setApellidoMaterno("González");

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        turnosAtencionMedicaEntity.setConsultorio(consultorioEntity);
        turnosAtencionMedicaEntity.setTurno(turnoEntity);
        turnosAtencionMedicaEntity.setMedico(medicoEntity);
        turnosAtencionMedicaEntity.setNumeroFichasDisponible(20);
        turnosAtencionMedicaEntity.setFecha(sdf.parse("2024-10-01"));

        pacienteEntity.setIdUsuario(2);
        pacienteEntity.setNombres("Carlos");
        pacienteEntity.setApellidoPaterno("Gutierrez");
        pacienteEntity.setApellidoMaterno("González");

    }
    @BeforeEach
    public void beforeEach() {
        MockitoAnnotations.openMocks(this);
    }
    
    @Test
    public void obtenerConsultasMedicasPaciente_ExistePaciente_True() {
        int idPaciente = 1;
        UsuarioEntity pacienteEntity = new UsuarioEntity();
        pacienteEntity.setIdUsuario(idPaciente);
        
        when(usuarioRepositoryJPA.findById(idPaciente)).thenReturn(Optional.of(pacienteEntity));
        
        ConsultaMedicaEntity consultaMedicaEntity=new ConsultaMedicaEntity();
        consultaMedicaEntity.setPaciente(pacienteEntity);
        consultaMedicaEntity.setTurnoAtencionMedica(turnosAtencionMedicaEntity);
        List<ConsultaMedicaEntity> consultasMedicas = new ArrayList<>();
        consultasMedicas.add(consultaMedicaEntity);

        when(consultaMedicaRepositoryJPA.findByPaciente(pacienteEntity)).thenReturn(consultasMedicas);

        List<ConsultaMedicaDto> result = consultasMedicasService.obtenerConsultasMedicasPaciente(idPaciente);
        
        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    public void obtenerConsultasMedicasPaciente_ExistePaciente_False() {
        int idPaciente = 1;

        when(usuarioRepositoryJPA.findById(idPaciente)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            consultasMedicasService.obtenerConsultasMedicasPaciente(idPaciente);
        });

        assertEquals("Paciente no encontrado", exception.getMessage());
    }

    @Test
    public void crearConsultaMedica_ExistePacienteYTurno_True() {
        ConsultaMedicaDto consultaDto = new ConsultaMedicaDto();
        consultaDto.setIdPaciente(1);
        consultaDto.setIdTurnoAtencionMedica(2);
        
        when(usuarioRepositoryJPA.findById(1)).thenReturn(Optional.of(pacienteEntity));
        when(turnosAtencionMedicaRepositoryJPA.findById(2)).thenReturn(Optional.of(turnosAtencionMedicaEntity));
        when(consultaMedicaRepositoryJPA.findAllByTurnoAtencionMedica(turnosAtencionMedicaEntity)).thenReturn(new ArrayList<>());

        ConsultaMedicaDto result = consultasMedicasService.crearConsultaMedica(consultaDto);
        
        assertNotNull(result);
    }

    @Test
    public void eliminarConsultaMedica_ExisteConsultaMedica_True() {
        int idConsultaMedica = 1;
        consultasMedicasService.eliminarConsultaMedica(idConsultaMedica);        
    }
}
