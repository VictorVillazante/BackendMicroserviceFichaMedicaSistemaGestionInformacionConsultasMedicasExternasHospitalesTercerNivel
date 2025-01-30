package com.example.microserviciofichasmedicas.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.example.microserviciofichasmedicas.configuration.websockets.handlers.NotificacionesWebSocketHandler;
import com.example.microserviciofichasmedicas.controller.NotificacionesController;
import com.example.microserviciofichasmedicas.model.ConsultaMedicaEntity;
import com.example.microserviciofichasmedicas.model.FichasMedicasEntity;
import com.example.microserviciofichasmedicas.model.TurnosAtencionMedicaEntity;
import com.example.microserviciofichasmedicas.model.UsuarioEntity;
import com.example.microserviciofichasmedicas.model.dtos.ConsultaMedicaDto;
import com.example.microserviciofichasmedicas.model.dtos.NotificacionDto;
import com.example.microserviciofichasmedicas.model.dtos.NotificacionUsuarioDto;
import com.example.microserviciofichasmedicas.model.dtos.TurnoAtencionMedicaDto;
import com.example.microserviciofichasmedicas.repository.ConsultaMedicaRepositoryJPA;
import com.example.microserviciofichasmedicas.repository.TurnosAtencionMedicaRepositoryJPA;
import com.example.microserviciofichasmedicas.repository.UsuarioRepositoryJPA;
import com.example.microserviciofichasmedicas.util.ConsultasMedicasSpecification;
import com.example.microserviciofichasmedicas.util.exceptions.BusinessValidationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.transaction.Transactional;

@Service
public class ConsultasMedicasService {
    @Autowired
    UsuarioRepositoryJPA usuarioRepositoryJPA;
    @Autowired
    ConsultaMedicaRepositoryJPA consultaMedicaRepositoryJPA;
    @Autowired
    TurnosAtencionMedicaRepositoryJPA turnosAtencionMedicaRepositoryJPA;
    @Autowired
    ConvertirTiposDatosService convertirTiposDatosService;
    @Autowired  
    TurnosAtencionMedicaService turnosAtencionMedicaService;
    @Autowired
    NotificacionesController notificacionesController;
    @Autowired
    NotificacionesService notificacionesService;
    @Autowired
    NotificacionesUsuariosService notificacionesUsuariosService;
    @Autowired
    EmailService emailService;
    public List<ConsultaMedicaDto> obtenerConsultasMedicasPaciente(String idPaciente, String fechaInicio, String fechaFin, Integer page, Integer size) {
        UsuarioEntity pacienteEntity = usuarioRepositoryJPA.findByIdUsuarioAndDeletedAtIsNull(idPaciente)
        .orElseThrow(() -> new RuntimeException("Paciente no encontrado"));
        List<ConsultaMedicaEntity>consultas=new ArrayList<>();
        Specification<ConsultaMedicaEntity> spec = Specification.where(ConsultasMedicasSpecification.obtenerConsultasMedicasDePacientePorParametros(idPaciente, fechaInicio, fechaFin));
        if(page!=null && size!=null){
            Pageable pageable = PageRequest.of(page, size);
            Page<ConsultaMedicaEntity> consultasMedicasEntitiesPage=consultaMedicaRepositoryJPA.findAll(spec,pageable);
            consultas=consultasMedicasEntitiesPage.getContent();
        }else{
            consultas=consultaMedicaRepositoryJPA.findAll(spec);
        }        
        List<ConsultaMedicaDto> consultasMedicasDto=consultas.stream().map((consultaMedicaEntity->new ConsultaMedicaDto().convertirConsultaMedicaEntityConsultaMedicaDto(consultaMedicaEntity))).toList();
        return consultasMedicasDto;
    }
    public ConsultaMedicaDto obtenerConsultaMedicaPorId(int idConsultaMedica) throws Exception {
        ConsultaMedicaEntity consultaMedicaEntity = consultaMedicaRepositoryJPA.findByIdConsultaMedicaAndDeletedAtIsNull(idConsultaMedica)
        .orElseThrow(() -> new Exception("Consulta medica no encontrada"));
        return new ConsultaMedicaDto().convertirConsultaMedicaEntityConsultaMedicaDto(consultaMedicaEntity);
    }
    Logger logger=LoggerFactory.getLogger(ConsultasMedicasService.class);
    public List<ConsultaMedicaDto> obtenerConsultasMedicasPorMedico(String idMedico, String fechaInicio, String fechaFin, Integer page, Integer size) {
        UsuarioEntity pacienteEntity = usuarioRepositoryJPA.findByIdUsuarioAndDeletedAtIsNull(idMedico)
        .orElseThrow(() -> new RuntimeException("Paciente no encontrado"));
        List<ConsultaMedicaEntity>consultas=new ArrayList<>();
        Specification<ConsultaMedicaEntity> spec = Specification.where(ConsultasMedicasSpecification.obtenerConsultasMedicasDeMedicoPorParametros(idMedico, fechaInicio, fechaFin));
        if(page!=null && size!=null){
            Pageable pageable = PageRequest.of(page, size);
            Page<ConsultaMedicaEntity> consultasMedicasEntitiesPage=consultaMedicaRepositoryJPA.findAll(spec,pageable);
            consultas=consultasMedicasEntitiesPage.getContent();
        }else{
            consultas=consultaMedicaRepositoryJPA.findAll(spec);
        }        
        List<ConsultaMedicaDto> consultasMedicasDto=consultas.stream().map((consultaMedicaEntity->new ConsultaMedicaDto().convertirConsultaMedicaEntityConsultaMedicaDto(consultaMedicaEntity))).toList();
        return consultasMedicasDto;
    }
    public void eliminarConsultaMedica(int idConsultaMedica) throws Exception {
        ConsultaMedicaEntity consultaMedicaEntity = consultaMedicaRepositoryJPA.findByIdConsultaMedicaAndDeletedAtIsNull(idConsultaMedica)
        .orElseThrow(() -> new Exception("Consulta medica no encontrada"));
        TurnosAtencionMedicaEntity turnosAtencionMedicaEntity = turnosAtencionMedicaRepositoryJPA.findByIdTurnoAtencionMedicaAndDeletedAtIsNull(consultaMedicaEntity.getTurnoAtencionMedica().getIdTurnoAtencionMedica())
        .orElseThrow(() -> new Exception("Turno atencion medica no encontrado"));
        TurnoAtencionMedicaDto turnoAtencionActualizado=new TurnoAtencionMedicaDto().convertirTurnoAtencionMedicaEntityTurnoAtencionMedicaDto(turnosAtencionMedicaEntity);
        turnoAtencionActualizado.setNumeroFichasDisponible((turnoAtencionActualizado.getNumeroFichasDisponible()<turnoAtencionActualizado.getNumeroFichasAsignado())?turnoAtencionActualizado.getNumeroFichasDisponible()+1:turnoAtencionActualizado.getNumeroFichasAsignado());
        turnosAtencionMedicaService.actualizarTurnoAtencionMedicaDto(turnoAtencionActualizado);
        consultaMedicaEntity.markAsDeleted();
        consultaMedicaRepositoryJPA.save(consultaMedicaEntity);
    }
    private ObjectMapper objectMapper = new ObjectMapper();
    public ConsultaMedicaDto crearConsultaMedica(ConsultaMedicaDto consultaMedicaDto) throws JsonMappingException, JsonProcessingException { 
        UsuarioEntity pacienteEntity = usuarioRepositoryJPA.findByIdUsuarioAndDeletedAtIsNull(consultaMedicaDto.getIdPaciente())
        .orElseThrow(() -> new RuntimeException("Paciente no encontrado"));
        TurnosAtencionMedicaEntity turnosAtencionMedicaEntity = turnosAtencionMedicaRepositoryJPA.findByIdTurnoAtencionMedicaAndDeletedAtIsNull(consultaMedicaDto.getIdTurnoAtencionMedica())
        .orElseThrow(() -> new RuntimeException("Turno atencion medica no encontrado"));
        ConsultaMedicaEntity consultaNoAprobada = consultaMedicaRepositoryJPA.obtenerConsultasNoAprobadas(consultaMedicaDto.getIdPaciente(),consultaMedicaDto.getIdTurnoAtencionMedica());
        // if(consultaNoAprobada!=null){
        //     throw new RuntimeException("El paciente tiene una consulta no aprobada");
        // }
        List<ConsultaMedicaEntity> consultaMedicaEntities = consultaMedicaRepositoryJPA.findAllByTurnoAtencionMedicaAndDeletedAtIsNull(turnosAtencionMedicaEntity);
        List<ConsultaMedicaDto> consultasMedicasDelDiaObtenidasPorPaciente=obtenerConsultasMedicasPaciente(consultaMedicaDto.getIdPaciente(), turnosAtencionMedicaEntity.getFecha().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")), turnosAtencionMedicaEntity.getFecha().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")), null, null);
        if(consultasMedicasDelDiaObtenidasPorPaciente.size()>=1){
            throw new BusinessValidationException("El paciente ya tiene una consulta medica en la fecha seleccionada");
        }
        if(turnosAtencionMedicaEntity.getNumeroFichasDisponible()<=0){
            throw new BusinessValidationException("No hay fichas disponibles");
        }
        TurnoAtencionMedicaDto turnoAtencionActualizado=new TurnoAtencionMedicaDto().convertirTurnoAtencionMedicaEntityTurnoAtencionMedicaDto(turnosAtencionMedicaEntity);
        Map<String, Object> jsonData = objectMapper.readValue(turnosAtencionMedicaEntity.getConsultorio().getEspecialidad().getPermisos(), Map.class);
        Map<String, Object> permisosPacientes = (Map<String, Object>)jsonData.get("pacientes");
        List<String> sexoPermitido = (List<String>) permisosPacientes.get("sexoPermitido");
        Map<String, Integer> edadPermitida = (Map<String, Integer>) permisosPacientes.get("edadPermitida");
        if(!sexoPermitido.contains(pacienteEntity.getSexo())){
            throw new BusinessValidationException("La especialidad no atiende al sexo del paciente");
        }
        if(edadPermitida.get("min")!=null && edadPermitida.get("min")>=pacienteEntity.getEdad()){
            throw new BusinessValidationException("La especialidad no atiende a la edad del paciente");
        }
        if(edadPermitida.get("max")!=null   && edadPermitida.get("max")<=pacienteEntity.getEdad()){
            throw new BusinessValidationException("La especialidad no atiende a la edad del paciente");
        }
        turnoAtencionActualizado.setNumeroFichasDisponible(turnoAtencionActualizado.getNumeroFichasDisponible()-1);
        turnosAtencionMedicaService.actualizarTurnoAtencionMedicaDto(turnoAtencionActualizado);

        ConsultaMedicaEntity consultaMedicaEntity = new ConsultaMedicaEntity();
        consultaMedicaEntity.setPaciente(pacienteEntity);
        consultaMedicaEntity.setTurnoAtencionMedica(turnosAtencionMedicaEntity);
        consultaMedicaEntity.setNumeroFicha(consultaMedicaEntities.size()+1);
        consultaMedicaEntity.setCodigoFichaMedica("");
        consultaMedicaRepositoryJPA.save(consultaMedicaEntity);
        return new ConsultaMedicaDto().convertirConsultaMedicaEntityConsultaMedicaDto(consultaMedicaEntity);
    }
    public ConsultaMedicaDto actualizarEstadoConsultaMedica(int idConsultaMedica, ConsultaMedicaDto consultaMedicaDto) {
        ConsultaMedicaEntity consultaMedicaEntity = consultaMedicaRepositoryJPA.findByIdConsultaMedicaAndDeletedAtIsNull(idConsultaMedica)
        .orElseThrow(() -> new RuntimeException("Consulta medica no encontrada"));
        consultaMedicaEntity.setEstado(consultaMedicaDto.getEstado());
        consultaMedicaRepositoryJPA.save(consultaMedicaEntity);
        return new ConsultaMedicaDto().convertirConsultaMedicaEntityConsultaMedicaDto(consultaMedicaEntity);
    }
    public Page<ConsultaMedicaDto> obtenerConsultasMedicas(String nombreEspecialidad, String nombreMedico,
            String nombrePaciente, String fechaInicio, String fechaFin, String hora,Integer page,
            Integer size) {
        Pageable pageable = Pageable.unpaged();
        if(page!=null && size!=null){
            pageable = PageRequest.of(page, size);
        } 
        Specification<ConsultaMedicaEntity> spec = Specification.where(ConsultasMedicasSpecification.obtenerConsultasMedicas(convertirTiposDatosService.convertirStringADate(fechaInicio),convertirTiposDatosService.convertirStringADate(fechaFin),convertirTiposDatosService.convertirStringALocalTime(hora),nombreEspecialidad,nombreMedico,nombrePaciente));
       
        return consultaMedicaRepositoryJPA.findAll(spec,pageable).map(ConsultaMedicaDto::convertirConsultaMedicaEntityConsultaMedicaDto);
    }
    public ConsultaMedicaDto obtenerConsultaEnCurso(int idConsultaMedica) {
        ConsultaMedicaEntity consultaMedicaEntity = consultaMedicaRepositoryJPA.findByIdConsultaMedicaAndDeletedAtIsNull(idConsultaMedica)
        .orElseThrow(() -> new RuntimeException("Consulta medica no encontrada"));
        ConsultaMedicaEntity consultaEnCurso = consultaMedicaRepositoryJPA.obtenerConsultaEnCurso(consultaMedicaEntity.getTurnoAtencionMedica().getIdTurnoAtencionMedica());
        if(consultaEnCurso==null){
            throw new RuntimeException("No hay consultas en curso");
        }
        return new ConsultaMedicaDto().convertirConsultaMedicaEntityConsultaMedicaDto(consultaEnCurso);
    }
    public void rechazarPeticionesFueraFecha() {
        consultaMedicaRepositoryJPA.rechazarPeticionesFueraFecha(LocalDate.now());
    }

    @Transactional
    public void eliminarConsultasDeTurnoAtencionMedica(int idTurnoAtencionMedica) throws Exception {
        TurnosAtencionMedicaEntity turnosAtencionMedicaEntity = turnosAtencionMedicaRepositoryJPA.findByIdTurnoAtencionMedicaAndDeletedAtIsNull(idTurnoAtencionMedica).orElseThrow(()->new RuntimeException("Turno atencion medica no encontrado"));
        List<ConsultaMedicaEntity>consultaMedicaEntities=consultaMedicaRepositoryJPA.findAllByTurnoAtencionMedicaAndDeletedAtIsNull(turnosAtencionMedicaEntity);
        NotificacionDto notificacionDto = new NotificacionDto();
        notificacionDto.setTitulo("Turno atencion medica eliminado");
        notificacionDto.setDescripcion("Su consulta fue eliminada porque el turno de atencion medica de "+turnosAtencionMedicaEntity.getConsultorio().getEspecialidad().getNombre()+
        " del turno "+turnosAtencionMedicaEntity.getTurno().getHoraInicio()+ " a "+turnosAtencionMedicaEntity.getTurno().getHoraFin()+" tambien fue eliminado"
        );
        NotificacionDto notificacionCreada=notificacionesService.crearNotificacion(notificacionDto);
        for(ConsultaMedicaEntity consultaMedicaEntit:consultaMedicaEntities){
            NotificacionUsuarioDto notificacionUsuarioDto = new NotificacionUsuarioDto();
            notificacionUsuarioDto.setIdNotificacion(notificacionCreada.getIdNotificacion());
            notificacionUsuarioDto.setIdUsuario(consultaMedicaEntit.getPaciente().getIdUsuario());
            notificacionUsuarioDto.setLeido(false);
            notificacionesUsuariosService.crearNotificacionUsuario(notificacionUsuarioDto);
            if(consultaMedicaEntit.getPaciente().getEmail()!=null){
                emailService.sendSimpleEmail(consultaMedicaEntit.getPaciente().getEmail(), notificacionDto.getTitulo(),notificacionDto.getDescripcion());
            }

            // String consultaString = objectMapper.writeValueAsString(new ConsultaMedicaDto().convertirConsultaMedicaEntityConsultaMedicaDto(consultaMedicaEntit));
            // Map<String, Object> notificacionPayload = new HashMap<>();
            // notificacionPayload.put("mensaje", "consulta eliminada");
            // notificacionPayload.put("elemento", consultaString);
            // String payload = objectMapper.writeValueAsString(notificacionPayload);
            // notificacionesController.sendNotificationToUser(consultaMedicaEntit.getPaciente().getIdUsuario(),payload);
        }
        consultaMedicaRepositoryJPA.markAsDeletedAllConsultasMedicasFromTurnoAtencionMedica(idTurnoAtencionMedica,new Date());
    }
    
}
