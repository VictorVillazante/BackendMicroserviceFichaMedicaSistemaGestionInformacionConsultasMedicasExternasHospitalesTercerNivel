// package com.example.microserviciofichasmedicas.repository;

// import static org.assertj.core.api.Assertions.assertThat;

// import java.text.ParseException;
// import java.text.SimpleDateFormat;
// import java.time.LocalTime;
// import java.time.format.DateTimeFormatter;
// import java.util.List;

// import org.junit.jupiter.api.BeforeAll;
// import org.junit.jupiter.api.Test;
// import org.junit.jupiter.api.TestInstance;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
// import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
// import org.springframework.test.annotation.Rollback;

// import com.example.microserviciofichasmedicas.model.ConsultaMedicaEntity;
// import com.example.microserviciofichasmedicas.model.ConsultorioEntity;
// import com.example.microserviciofichasmedicas.model.EspecialidadesEntity;
// import com.example.microserviciofichasmedicas.model.TurnoEntity;
// import com.example.microserviciofichasmedicas.model.TurnosAtencionMedicaEntity;
// import com.example.microserviciofichasmedicas.model.UsuarioEntity;

// @TestInstance(TestInstance.Lifecycle.PER_CLASS)
// @AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
// @DataJpaTest
// public class ConsultaMedicaRepositoryJPATest {

//     @Autowired
//     private ConsultaMedicaRepositoryJPA consultaMedicaRepositoryJPA;

//     @Autowired
//     private UsuarioRepositoryJPA usuarioRepositoryJPA;

//     @Autowired
//     private TurnosAtencionMedicaRepositoryJPA turnosAtencionMedicaRepositoryJPA;

//     @Autowired
//     private TurnosRepositoryJPA turnosRepositoryJPA;

//     @Autowired
//     private ConsultoriosRepositoryJPA consultoriosRepositoryJPA;

//     @Autowired
//     private EspecialidadesRepositoryJPA especialidadesRepositoryJPA;
    
//     TurnosAtencionMedicaEntity turnoAtencionMedicaEntity=new TurnosAtencionMedicaEntity();
//     TurnoEntity turnoEntity=new TurnoEntity();
//     UsuarioEntity medicoEntity=new UsuarioEntity();
//     UsuarioEntity pacienteEntity=new UsuarioEntity();
//     ConsultorioEntity consultorioEntity=new ConsultorioEntity();
//     EspecialidadesEntity especialidadesEntity=new EspecialidadesEntity();
    
//     DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

//     @BeforeAll
//     void beforeAll() throws ParseException{
//         especialidadesEntity.setNombre("Pediatría");

//         consultorioEntity.setNombre("Consultorio A");
//         consultorioEntity.setEspecialidad(especialidadesEntity);

//         turnoEntity.setNombre("Mañana");
//         turnoEntity.setHoraInicio(LocalTime.of(8, 0));
//         turnoEntity.setHoraFin(LocalTime.of(10, 0));

//         medicoEntity.setNombres("Juan");
//         medicoEntity.setApellidoPaterno("Pérez");
//         medicoEntity.setApellidoMaterno("González");

//         SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//         turnoAtencionMedicaEntity.setConsultorio(consultorioEntity);
//         turnoAtencionMedicaEntity.setTurno(turnoEntity);
//         turnoAtencionMedicaEntity.setMedico(medicoEntity);
//         turnoAtencionMedicaEntity.setNumeroFichasDisponible(20);
//         turnoAtencionMedicaEntity.setFecha(sdf.parse("2024-10-01"));

//         pacienteEntity.setNombres("Carlos");
//         pacienteEntity.setApellidoPaterno("Gutierrez");
//         pacienteEntity.setApellidoMaterno("González");

//     }

//     @Test
//     @Rollback(value = true)
//     public void findByPaciente_existenConsultasPaciente_true() {
//         usuarioRepositoryJPA.save(pacienteEntity);
//         usuarioRepositoryJPA.save(medicoEntity);
//         turnosRepositoryJPA.save(turnoEntity);
//         especialidadesRepositoryJPA.save(especialidadesEntity);
//         consultoriosRepositoryJPA.save(consultorioEntity);
//         turnosAtencionMedicaRepositoryJPA.save(turnoAtencionMedicaEntity);

//         ConsultaMedicaEntity consulta = new ConsultaMedicaEntity();
//         consulta.setPaciente(pacienteEntity);
//         consulta.setTurnoAtencionMedica(turnoAtencionMedicaEntity);
//         consultaMedicaRepositoryJPA.save(consulta);

//         List<ConsultaMedicaEntity> consultas = consultaMedicaRepositoryJPA.findByPaciente(pacienteEntity);
//         assertThat(consultas.size()).isPositive();
//         assertThat(consultas.get(0).getPaciente().getIdUsuario()).isEqualTo(pacienteEntity.getIdUsuario());
//     }

//     @Test
//     public void obtenerFichasMedicasPorIdMedico_ExisteMedico_True() {
//         usuarioRepositoryJPA.save(pacienteEntity);

//         usuarioRepositoryJPA.save(medicoEntity);
//         turnosRepositoryJPA.save(turnoEntity);
//         especialidadesRepositoryJPA.save(especialidadesEntity);
//         consultoriosRepositoryJPA.save(consultorioEntity);
//         turnosAtencionMedicaRepositoryJPA.save(turnoAtencionMedicaEntity);

//         ConsultaMedicaEntity consulta = new ConsultaMedicaEntity();
//         consulta.setTurnoAtencionMedica(turnoAtencionMedicaEntity);
//         consulta.setPaciente(pacienteEntity);
//         consultaMedicaRepositoryJPA.save(consulta);

//         List<ConsultaMedicaEntity> consultas = consultaMedicaRepositoryJPA.obtenerFichasMedicasPorIdMedico(consulta.getTurnoAtencionMedica().getMedico().getIdUsuario());

//         assertThat(consultas).isNotEmpty();
//     }

//     @Test
//     public void findAllByTurnoAtencionMedica_ExisteTurnoAtencionMedica_True() {
//         usuarioRepositoryJPA.save(pacienteEntity);

//         usuarioRepositoryJPA.save(medicoEntity);
//         turnosRepositoryJPA.save(turnoEntity);
//         especialidadesRepositoryJPA.save(especialidadesEntity);
//         consultoriosRepositoryJPA.save(consultorioEntity);
//         turnosAtencionMedicaRepositoryJPA.save(turnoAtencionMedicaEntity);

//         ConsultaMedicaEntity consulta = new ConsultaMedicaEntity();
//         consulta.setTurnoAtencionMedica(turnoAtencionMedicaEntity);
//         consulta.setPaciente(pacienteEntity);
//         consultaMedicaRepositoryJPA.save(consulta);

//         ConsultaMedicaEntity consulta2 = new ConsultaMedicaEntity();
//         consulta2.setTurnoAtencionMedica(turnoAtencionMedicaEntity);
//         consulta2.setPaciente(pacienteEntity);
//         consultaMedicaRepositoryJPA.save(consulta2);

//         List<ConsultaMedicaEntity> consultas = consultaMedicaRepositoryJPA.findAllByTurnoAtencionMedica(turnoAtencionMedicaEntity);

//         assertThat(consultas).hasSize(2);
//     }
// }