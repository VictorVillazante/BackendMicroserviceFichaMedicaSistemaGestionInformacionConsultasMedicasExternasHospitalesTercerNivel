package com.example.microserviciofichasmedicas.model.dtos;


import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import com.example.microserviciofichasmedicas.model.TurnosAtencionMedicaEntity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class TurnoAtencionMedicaDto {
    private Integer idTurnoAtencionMedica;
    private Integer numeroFichasDisponible;
    private Integer numeroFichasAsignado;
    private String fecha;
    private Integer idConsultorio;
    private String nombreConsultorio;
    private Integer idTurno;
    private String nombreTurno;
    private String horaInicio;
    private String horaFin;
    private String idMedico;
    private String nombreMedico;
    private Integer idEspecialidad;
    private String nombreEspecialidad;
    public TurnoAtencionMedicaDto convertirTurnoAtencionMedicaEntityTurnoAtencionMedicaDto(TurnosAtencionMedicaEntity turnoAtencionMedicaEntity){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        TurnoAtencionMedicaDto turnoAtencionMedicaDto=new TurnoAtencionMedicaDto();
        turnoAtencionMedicaDto.setIdTurnoAtencionMedica(turnoAtencionMedicaEntity.getIdTurnoAtencionMedica());
        turnoAtencionMedicaDto.setNumeroFichasDisponible(turnoAtencionMedicaEntity.getNumeroFichasDisponible());
        turnoAtencionMedicaDto.setNumeroFichasAsignado(turnoAtencionMedicaEntity.getNumeroFichasAsignado());
        turnoAtencionMedicaDto.setFecha(turnoAtencionMedicaEntity.getFecha().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        // turnoAtencionMedicaDto.setFecha(turnoAtencionMedicaEntity.getFecha());
        turnoAtencionMedicaDto.setIdConsultorio(turnoAtencionMedicaEntity.getConsultorio().getIdConsultorio());
        turnoAtencionMedicaDto.setNombreConsultorio(turnoAtencionMedicaEntity.getConsultorio().getNombre());
        turnoAtencionMedicaDto.setIdTurno(turnoAtencionMedicaEntity.getTurno().getIdTurno());
        turnoAtencionMedicaDto.setNombreTurno(turnoAtencionMedicaEntity.getTurno().getNombre());
        turnoAtencionMedicaDto.setHoraInicio(turnoAtencionMedicaEntity.getTurno().getHoraInicio().format(formatter));
        turnoAtencionMedicaDto.setHoraFin(turnoAtencionMedicaEntity.getTurno().getHoraFin().format(formatter));
        turnoAtencionMedicaDto.setIdMedico(turnoAtencionMedicaEntity.getMedico().getIdUsuario());
        turnoAtencionMedicaDto.setNombreMedico(turnoAtencionMedicaEntity.getMedico().getNombres()+" "+turnoAtencionMedicaEntity.getMedico().getApellidoPaterno()+" "+turnoAtencionMedicaEntity.getMedico().getApellidoMaterno());
        turnoAtencionMedicaDto.setIdEspecialidad(turnoAtencionMedicaEntity.getConsultorio().getEspecialidad().getIdEspecialidad());
        turnoAtencionMedicaDto.setNombreEspecialidad(turnoAtencionMedicaEntity.getConsultorio().getEspecialidad().getNombre());
        return turnoAtencionMedicaDto;
    }
}
