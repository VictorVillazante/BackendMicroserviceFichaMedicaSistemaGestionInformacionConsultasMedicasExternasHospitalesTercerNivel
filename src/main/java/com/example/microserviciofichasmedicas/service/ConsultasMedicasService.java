package com.example.microserviciofichasmedicas.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.microserviciofichasmedicas.model.ConsultaMedicaEntity;
import com.example.microserviciofichasmedicas.model.FichasMedicasEntity;
import com.example.microserviciofichasmedicas.model.TurnosAtencionMedicaEntity;
import com.example.microserviciofichasmedicas.model.UsuarioEntity;
import com.example.microserviciofichasmedicas.model.dtos.ConsultaMedicaDto;
import com.example.microserviciofichasmedicas.repository.ConsultaMedicaRepositoryJPA;
import com.example.microserviciofichasmedicas.repository.TurnosAtencionMedicaRepositoryJPA;
import com.example.microserviciofichasmedicas.repository.UsuarioRepositoryJPA;

@Service
public class ConsultasMedicasService {
    @Autowired
    UsuarioRepositoryJPA usuarioRepositoryJPA;
    @Autowired
    ConsultaMedicaRepositoryJPA consultaMedicaRepositoryJPA;
    @Autowired
    TurnosAtencionMedicaRepositoryJPA turnosAtencionMedicaRepositoryJPA;
    public List<ConsultaMedicaDto> obtenerConsultasMedicasPaciente(int idPaciente) {
        UsuarioEntity pacienteEntity = usuarioRepositoryJPA.findById(idPaciente)
        .orElseThrow(() -> new RuntimeException("Paciente no encontrado"));
        List<ConsultaMedicaEntity> consultasMedicasEntities=consultaMedicaRepositoryJPA.findByPaciente(pacienteEntity);
        List<ConsultaMedicaDto> consultasMedicasDto=consultasMedicasEntities.stream().map((consultaMedicaEntity->new ConsultaMedicaDto().convertirConsultaMedicaEntityConsultaMedicaDto(consultaMedicaEntity))).toList();
        return consultasMedicasDto;
    }
    public ConsultaMedicaDto obtenerConsultaMedicaPorId(int idConsultaMedica) {
        ConsultaMedicaEntity consultaMedicaEntity = consultaMedicaRepositoryJPA.findById(idConsultaMedica)
        .orElseThrow(() -> new RuntimeException("Consulta medica no encontrada"));
        return new ConsultaMedicaDto().convertirConsultaMedicaEntityConsultaMedicaDto(consultaMedicaEntity);
    }
    public List<ConsultaMedicaDto> obtenerConsultasMedicasPorMedico(int idMedico) {
        List<ConsultaMedicaEntity> consultas = consultaMedicaRepositoryJPA.obtenerFichasMedicasPorIdMedico(idMedico);
        return consultas.stream()
                .map(consultaMedicaEntity -> new ConsultaMedicaDto().convertirConsultaMedicaEntityConsultaMedicaDto(consultaMedicaEntity))
                .toList();
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
        ConsultaMedicaEntity consultaMedicaEntity = new ConsultaMedicaEntity();
        consultaMedicaEntity.setPaciente(pacienteEntity);
        consultaMedicaEntity.setTurnoAtencionMedica(turnosAtencionMedicaEntity);
        consultaMedicaEntity.setNumeroFicha(consultaMedicaEntities.size()+1);
        consultaMedicaEntity.setCodigoFichaMedica("");
        consultaMedicaRepositoryJPA.save(consultaMedicaEntity);
        return new ConsultaMedicaDto().convertirConsultaMedicaEntityConsultaMedicaDto(consultaMedicaEntity);
    }
    
}
