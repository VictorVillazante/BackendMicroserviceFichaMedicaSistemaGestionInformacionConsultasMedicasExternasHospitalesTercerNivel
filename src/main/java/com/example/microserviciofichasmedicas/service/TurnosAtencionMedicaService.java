

package com.example.microserviciofichasmedicas.service;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.example.microserviciofichasmedicas.model.ConsultorioEntity;
import com.example.microserviciofichasmedicas.model.TurnoEntity;
import com.example.microserviciofichasmedicas.model.TurnosAtencionMedicaEntity;
import com.example.microserviciofichasmedicas.model.UsuarioEntity;
import com.example.microserviciofichasmedicas.model.dtos.TurnoAtencionMedicaDto;
import com.example.microserviciofichasmedicas.repository.ConsultoriosRepositoryJPA;
import com.example.microserviciofichasmedicas.repository.TurnosAtencionMedicaRepositoryJPA;
import com.example.microserviciofichasmedicas.repository.TurnosRepositoryJPA;
import com.example.microserviciofichasmedicas.repository.UsuarioRepositoryJPA;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;

@Service
public class TurnosAtencionMedicaService {
    @Autowired
    private TurnosAtencionMedicaRepositoryJPA turnosAtencionMedicaRepositoryJPA;
    @Autowired
    private TurnosRepositoryJPA turnosRepositoryJPA;
    @Autowired
    private ConsultoriosRepositoryJPA consultoriosRepositoryJPA;
    @Autowired
    private UsuarioRepositoryJPA usuarioRepositoryJPA;



    public TurnoAtencionMedicaDto actualizarTurnoAtencionMedicaDto(TurnoAtencionMedicaDto turnoAtencionMedicaDto) {
                // SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        TurnoEntity turnoEntity = turnosRepositoryJPA.findByIdTurnoAndDeletedAtIsNull(turnoAtencionMedicaDto.getIdTurno())
        .orElseThrow(() -> new RuntimeException("Turno no encontrado"));
        ConsultorioEntity consultorioEntity = consultoriosRepositoryJPA.findByIdConsultorioAndDeletedAtIsNull(turnoAtencionMedicaDto.getIdConsultorio())
        .orElseThrow(() -> new RuntimeException("Consultorio no encontrado"));
        UsuarioEntity medicoEntity = usuarioRepositoryJPA.findByIdUsuarioAndDeletedAtIsNull(turnoAtencionMedicaDto.getIdMedico())
        .orElseThrow(() -> new RuntimeException("Médico no encontrado"));
        TurnosAtencionMedicaEntity actualizadoEntity = turnosAtencionMedicaRepositoryJPA.findByIdTurnoAtencionMedicaAndDeletedAtIsNull(turnoAtencionMedicaDto.getIdTurnoAtencionMedica())
        .orElseThrow(() -> new RuntimeException("Turno de atención médica no encontrado"));
        actualizadoEntity.setFecha(LocalDate.parse(turnoAtencionMedicaDto.getFecha(), formato));
        actualizadoEntity.setNumeroFichasDisponible(turnoAtencionMedicaDto.getNumeroFichasDisponible());
        actualizadoEntity.setNumeroFichasAsignado(turnoAtencionMedicaDto.getNumeroFichasAsignado());
        actualizadoEntity.setConsultorio(consultorioEntity);
        actualizadoEntity.setTurno(turnoEntity);
        actualizadoEntity.setMedico(medicoEntity);
        TurnosAtencionMedicaEntity savedEntity = turnosAtencionMedicaRepositoryJPA.save(actualizadoEntity);
        return new TurnoAtencionMedicaDto().convertirTurnoAtencionMedicaEntityTurnoAtencionMedicaDto(savedEntity);
    }

    
}