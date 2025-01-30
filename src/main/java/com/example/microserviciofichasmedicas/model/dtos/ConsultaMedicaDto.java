package com.example.microserviciofichasmedicas.model.dtos;

import java.util.Date;

import com.example.microserviciofichasmedicas.model.ConsultaMedicaEntity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import lombok.experimental.Accessors;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class ConsultaMedicaDto {
    int idConsultaMedica;
    String codigoFichaMedica;
    int numeroFicha;
    String estado;
    String permisos;
    int idTurnoAtencionMedica;
    String fechaTurnoAtencionMedica;
    String idPaciente;
    String nombrePaciente;
    int idConsultorio;
    String nombreConsultorio;
    int idTurno;
    String nombreTurno;
    String horaInicio;
    String horaFin;
    String idMedico;
    String nombreMedico;
    int idEspecialidad;
    String nombreEspecialidad;
    Date createdAt;
    Date updatedAt;
    Date deletedAt;
    public static ConsultaMedicaDto convertirConsultaMedicaEntityConsultaMedicaDto(ConsultaMedicaEntity consultaMedicaEntity){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

        ConsultaMedicaDto consultaMedicaDto=new ConsultaMedicaDto();
        consultaMedicaDto.setIdConsultaMedica(consultaMedicaEntity.getIdConsultaMedica());
        consultaMedicaDto.setCodigoFichaMedica(consultaMedicaEntity.getCodigoFichaMedica());
        consultaMedicaDto.setEstado(consultaMedicaEntity.getEstado());
        consultaMedicaDto.setNumeroFicha(consultaMedicaEntity.getNumeroFicha());
        consultaMedicaDto.setPermisos(consultaMedicaEntity.getTurnoAtencionMedica().getConsultorio().getEspecialidad().getPermisos());
        consultaMedicaDto.setIdTurnoAtencionMedica(consultaMedicaEntity.getTurnoAtencionMedica().getIdTurnoAtencionMedica());
        consultaMedicaDto.setFechaTurnoAtencionMedica(consultaMedicaEntity.getTurnoAtencionMedica().getFecha().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        consultaMedicaDto.setIdPaciente(consultaMedicaEntity.getPaciente().getIdUsuario());
        consultaMedicaDto.setNombrePaciente(consultaMedicaEntity.getPaciente().getNombres()+" "+consultaMedicaEntity.getPaciente().getApellidoPaterno()+" "+consultaMedicaEntity.getPaciente().getApellidoMaterno());
        consultaMedicaDto.setIdConsultorio(consultaMedicaEntity.getTurnoAtencionMedica().getConsultorio().getIdConsultorio());
        consultaMedicaDto.setNombreConsultorio(consultaMedicaEntity.getTurnoAtencionMedica().getConsultorio().getNombre());
        consultaMedicaDto.setIdTurno(consultaMedicaEntity.getTurnoAtencionMedica().getTurno().getIdTurno());
        consultaMedicaDto.setNombreTurno(consultaMedicaEntity.getTurnoAtencionMedica().getTurno().getNombre());
        consultaMedicaDto.setHoraInicio(consultaMedicaEntity.getTurnoAtencionMedica().getTurno().getHoraInicio().format(formatter));
        consultaMedicaDto.setHoraFin(consultaMedicaEntity.getTurnoAtencionMedica().getTurno().getHoraFin().format(formatter));
        consultaMedicaDto.setIdMedico(consultaMedicaEntity.getTurnoAtencionMedica().getMedico().getIdUsuario());
        consultaMedicaDto.setNombreMedico(consultaMedicaEntity.getTurnoAtencionMedica().getMedico().getNombres()+" "+consultaMedicaEntity.getTurnoAtencionMedica().getMedico().getApellidoPaterno()+" "+consultaMedicaEntity.getTurnoAtencionMedica().getMedico().getApellidoMaterno());
        consultaMedicaDto.setIdEspecialidad(consultaMedicaEntity.getTurnoAtencionMedica().getConsultorio().getEspecialidad().getIdEspecialidad());
        consultaMedicaDto.setNombreEspecialidad(consultaMedicaEntity.getTurnoAtencionMedica().getConsultorio().getEspecialidad().getNombre());
        consultaMedicaDto.setCreatedAt(consultaMedicaEntity.getCreatedAt());
        consultaMedicaDto.setUpdatedAt(consultaMedicaEntity.getUpdatedAt());
        consultaMedicaDto.setDeletedAt(consultaMedicaEntity.getDeletedAt());

        return consultaMedicaDto;
    }
}
