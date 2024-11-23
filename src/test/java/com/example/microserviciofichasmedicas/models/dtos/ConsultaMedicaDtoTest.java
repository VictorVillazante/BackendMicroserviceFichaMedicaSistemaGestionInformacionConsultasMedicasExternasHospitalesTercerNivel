package com.example.microserviciofichasmedicas.models.dtos;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.meanbean.test.BeanTester;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.microserviciofichasmedicas.model.ConsultaMedicaEntity;
import com.example.microserviciofichasmedicas.model.ConsultorioEntity;
import com.example.microserviciofichasmedicas.model.EspecialidadesEntity;
import com.example.microserviciofichasmedicas.model.TurnoEntity;
import com.example.microserviciofichasmedicas.model.TurnosAtencionMedicaEntity;
import com.example.microserviciofichasmedicas.model.UsuarioEntity;
import com.example.microserviciofichasmedicas.model.dtos.ConsultaMedicaDto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ConsultaMedicaDtoTest {
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

    @Test
    void convertirConsultaMedicaEntityACuentaMedicaDto_consultaEntityCorrecta_True() {

        ConsultaMedicaEntity consultaMedicaEntity = new ConsultaMedicaEntity();
        consultaMedicaEntity.setPaciente(pacienteEntity);
        consultaMedicaEntity.setCodigoFichaMedica("idofjslk");
        consultaMedicaEntity.setTurnoAtencionMedica(turnosAtencionMedicaEntity);
        consultaMedicaEntity.setNumeroFicha(1);

        ConsultaMedicaDto consultaMedicaDto = new ConsultaMedicaDto().convertirConsultaMedicaEntityConsultaMedicaDto(consultaMedicaEntity);
        assertEquals(consultaMedicaEntity.getIdConsultaMedica(), consultaMedicaDto.getIdConsultaMedica());
        assertEquals(consultaMedicaEntity.getCodigoFichaMedica(), consultaMedicaDto.getCodigoFichaMedica());
        assertEquals(consultaMedicaEntity.getNumeroFicha(), consultaMedicaDto.getNumeroFicha());
        assertEquals(consultaMedicaEntity.getTurnoAtencionMedica().getIdTurnoAtencionMedica(), consultaMedicaDto.getIdTurnoAtencionMedica());
        assertEquals(consultaMedicaEntity.getTurnoAtencionMedica().getFecha(), consultaMedicaDto.getFechaTurnoAtencionMedica());
        assertEquals(consultaMedicaEntity.getPaciente().getIdUsuario(), consultaMedicaDto.getIdPaciente());
        assertEquals(consultaMedicaEntity.getPaciente().getNombres()+" "+consultaMedicaEntity.getPaciente().getApellidoPaterno()+" "+consultaMedicaEntity.getPaciente().getApellidoMaterno(), consultaMedicaDto.getNombrePaciente());
        assertEquals(consultaMedicaEntity.getTurnoAtencionMedica().getConsultorio().getIdConsultorio(), consultaMedicaDto.getIdConsultorio());
        assertEquals(consultaMedicaEntity.getTurnoAtencionMedica().getConsultorio().getNombre(), consultaMedicaDto.getNombreConsultorio());
        assertEquals(consultaMedicaEntity.getTurnoAtencionMedica().getTurno().getIdTurno(), consultaMedicaDto.getIdTurno());
        assertEquals(consultaMedicaEntity.getTurnoAtencionMedica().getTurno().getNombre(), consultaMedicaDto.getNombreTurno());
        assertEquals(consultaMedicaEntity.getTurnoAtencionMedica().getTurno().getHoraInicio().format(formatter), consultaMedicaDto.getHoraInicio());
        assertEquals(consultaMedicaEntity.getTurnoAtencionMedica().getTurno().getHoraFin().format(formatter), consultaMedicaDto.getHoraFin());
        assertEquals(consultaMedicaEntity.getTurnoAtencionMedica().getMedico().getIdUsuario(), consultaMedicaDto.getIdMedico());
        assertEquals(consultaMedicaEntity.getTurnoAtencionMedica().getMedico().getNombres()+" "+consultaMedicaEntity.getTurnoAtencionMedica().getMedico().getApellidoPaterno()+" "+consultaMedicaEntity.getTurnoAtencionMedica().getMedico().getApellidoMaterno(), consultaMedicaDto.getNombreMedico());
        assertEquals(consultaMedicaEntity.getTurnoAtencionMedica().getConsultorio().getEspecialidad().getIdEspecialidad(), consultaMedicaDto.getIdEspecialidad());
        assertEquals(consultaMedicaEntity.getTurnoAtencionMedica().getConsultorio().getEspecialidad().getNombre(), consultaMedicaDto.getNombreEspecialidad());
    }
    @Test
    void metodosBean_FuncionanMetodosBean_True(){
        BeanTester beanTester = new BeanTester();
        beanTester.testBean(ConsultaMedicaDto.class);
    }
}
