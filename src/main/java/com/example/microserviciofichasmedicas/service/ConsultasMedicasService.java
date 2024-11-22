package com.example.microserviciofichasmedicas.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.example.microserviciofichasmedicas.model.ConsultaMedicaEntity;
import com.example.microserviciofichasmedicas.model.FichasMedicasEntity;
import com.example.microserviciofichasmedicas.model.TurnosAtencionMedicaEntity;
import com.example.microserviciofichasmedicas.model.UsuarioEntity;
import com.example.microserviciofichasmedicas.model.dtos.ConsultaMedicaDto;
import com.example.microserviciofichasmedicas.repository.ConsultaMedicaRepositoryJPA;
import com.example.microserviciofichasmedicas.repository.TurnosAtencionMedicaRepositoryJPA;
import com.example.microserviciofichasmedicas.repository.UsuarioRepositoryJPA;
import com.example.microserviciofichasmedicas.util.ConsultasMedicasSpecification;

@Service
public class ConsultasMedicasService {
    @Autowired
    UsuarioRepositoryJPA usuarioRepositoryJPA;
    @Autowired
    ConsultaMedicaRepositoryJPA consultaMedicaRepositoryJPA;
    @Autowired
    TurnosAtencionMedicaRepositoryJPA turnosAtencionMedicaRepositoryJPA;
    public List<ConsultaMedicaDto> obtenerConsultasMedicasPaciente(int idPaciente, String fechaInicio, String fechaFin, Integer page, Integer size) {
        UsuarioEntity pacienteEntity = usuarioRepositoryJPA.findById(idPaciente)
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
    public ConsultaMedicaDto obtenerConsultaMedicaPorId(int idConsultaMedica) {
        ConsultaMedicaEntity consultaMedicaEntity = consultaMedicaRepositoryJPA.findById(idConsultaMedica)
        .orElseThrow(() -> new RuntimeException("Consulta medica no encontrada"));
        return new ConsultaMedicaDto().convertirConsultaMedicaEntityConsultaMedicaDto(consultaMedicaEntity);
    }
    Logger logger=LoggerFactory.getLogger(ConsultasMedicasService.class);
    public List<ConsultaMedicaDto> obtenerConsultasMedicasPorMedico(int idMedico, String fechaInicio, String fechaFin, Integer page, Integer size) {
        UsuarioEntity pacienteEntity = usuarioRepositoryJPA.findById(idMedico)
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
    public void eliminarConsultaMedica(int idConsultaMedica) {
        consultaMedicaRepositoryJPA.deleteById(idConsultaMedica);
    }
    public ConsultaMedicaDto crearConsultaMedica(ConsultaMedicaDto consultaMedicaDto) { 
        UsuarioEntity pacienteEntity = usuarioRepositoryJPA.findById(consultaMedicaDto.getIdPaciente())
        .orElseThrow(() -> new RuntimeException("Paciente no encontrado"));
        TurnosAtencionMedicaEntity turnosAtencionMedicaEntity = turnosAtencionMedicaRepositoryJPA.findById(consultaMedicaDto.getIdTurnoAtencionMedica())
        .orElseThrow(() -> new RuntimeException("Turno atencion medica no encontrado"));
        List<ConsultaMedicaEntity> consultaMedicaEntities = consultaMedicaRepositoryJPA.findAllByTurnoAtencionMedica(turnosAtencionMedicaEntity);
        List<ConsultaMedicaDto> consultasMedicasDelDiaObtenidasPorPaciente=obtenerConsultasMedicasPaciente(consultaMedicaDto.getIdPaciente(), turnosAtencionMedicaEntity.getFecha().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")), turnosAtencionMedicaEntity.getFecha().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")), null, null);
        if(consultasMedicasDelDiaObtenidasPorPaciente.size()>=1){
            throw new RuntimeException("El paciente ya tiene una consulta medica el dia de hoy");
        }
        if(consultaMedicaEntities.size()>=turnosAtencionMedicaEntity.getNumeroFichasDisponible()){
            throw new RuntimeException("No hay fichas disponibles");
        }
        ConsultaMedicaEntity consultaMedicaEntity = new ConsultaMedicaEntity();
        consultaMedicaEntity.setPaciente(pacienteEntity);
        consultaMedicaEntity.setTurnoAtencionMedica(turnosAtencionMedicaEntity);
        consultaMedicaEntity.setNumeroFicha(consultaMedicaEntities.size()+1);
        consultaMedicaEntity.setCodigoFichaMedica("");
        consultaMedicaRepositoryJPA.save(consultaMedicaEntity);
        return new ConsultaMedicaDto().convertirConsultaMedicaEntityConsultaMedicaDto(consultaMedicaEntity);
    }
    public ConsultaMedicaDto actualizarEstadoConsultaMedica(int idConsultaMedica, ConsultaMedicaDto consultaMedicaDto) {
        ConsultaMedicaEntity consultaMedicaEntity = consultaMedicaRepositoryJPA.findById(idConsultaMedica)
        .orElseThrow(() -> new RuntimeException("Consulta medica no encontrada"));
        consultaMedicaEntity.setEstado(consultaMedicaDto.getEstado());
        consultaMedicaRepositoryJPA.save(consultaMedicaEntity);
        return new ConsultaMedicaDto().convertirConsultaMedicaEntityConsultaMedicaDto(consultaMedicaEntity);
    }
    
}
