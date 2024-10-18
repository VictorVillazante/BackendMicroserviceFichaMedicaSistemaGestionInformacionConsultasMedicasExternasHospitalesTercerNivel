package com.example.microserviciofichasmedicas.model.dtos;

import java.util.Date;

import com.example.microserviciofichasmedicas.model.ConsultaMedicaEntity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalTime;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class ConsultaMedicaDto {
    int idConsultaMedica;
    String codigoFichaMedica;
    int numeroFicha;
    int idTurnoAtencionMedica;
    Date fechaTurnoAtencionMedica;
    int idPaciente;
    String nombrePaciente;
    int idConsultorio;
    String nombreConsultorio;
    int idTurno;
    String nombreTurno;
    LocalTime horaInicio;
    LocalTime horaFin;
    int idMedico;
    String nombreMedico;
    int idEspecialidad;
    String nombreEspecialidad;
    public ConsultaMedicaDto convertirConsultaMedicaEntityConsultaMedicaDto(ConsultaMedicaEntity consultaMedicaEntity){
        ConsultaMedicaDto consultaMedicaDto=new ConsultaMedicaDto();
        consultaMedicaDto.setIdConsultaMedica(consultaMedicaEntity.getIdConsultaMedica());
        consultaMedicaDto.setCodigoFichaMedica(consultaMedicaEntity.getCodigoFichaMedica());
        consultaMedicaDto.setNumeroFicha(consultaMedicaEntity.getNumeroFicha());
        consultaMedicaDto.setIdTurnoAtencionMedica(consultaMedicaEntity.getTurnoAtencionMedica().getIdTurnoAtencionMedica());
        consultaMedicaDto.setFechaTurnoAtencionMedica(consultaMedicaEntity.getTurnoAtencionMedica().getFecha());
        consultaMedicaDto.setIdPaciente(consultaMedicaEntity.getPaciente().getIdUsuario());
        consultaMedicaDto.setNombrePaciente(consultaMedicaEntity.getPaciente().getNombres()+" "+consultaMedicaEntity.getPaciente().getApellidoPaterno()+" "+consultaMedicaEntity.getPaciente().getApellidoMaterno());
        consultaMedicaDto.setIdConsultorio(consultaMedicaEntity.getTurnoAtencionMedica().getConsultorio().getIdConsultorio());
        consultaMedicaDto.setNombreConsultorio(consultaMedicaEntity.getTurnoAtencionMedica().getConsultorio().getNombre());
        consultaMedicaDto.setIdTurno(consultaMedicaEntity.getTurnoAtencionMedica().getTurno().getIdTurno());
        consultaMedicaDto.setNombreTurno(consultaMedicaEntity.getTurnoAtencionMedica().getTurno().getNombre());
        consultaMedicaDto.setHoraInicio(consultaMedicaEntity.getTurnoAtencionMedica().getTurno().getHoraInicio());
        consultaMedicaDto.setHoraFin(consultaMedicaEntity.getTurnoAtencionMedica().getTurno().getHoraFin());
        consultaMedicaDto.setIdMedico(consultaMedicaEntity.getTurnoAtencionMedica().getMedico().getIdUsuario());
        consultaMedicaDto.setNombreMedico(consultaMedicaEntity.getTurnoAtencionMedica().getMedico().getNombres()+" "+consultaMedicaEntity.getTurnoAtencionMedica().getMedico().getApellidoPaterno()+" "+consultaMedicaEntity.getTurnoAtencionMedica().getMedico().getApellidoMaterno());
        consultaMedicaDto.setIdEspecialidad(consultaMedicaEntity.getTurnoAtencionMedica().getConsultorio().getEspecialidad().getIdEspecialidad());
        consultaMedicaDto.setNombreEspecialidad(consultaMedicaEntity.getTurnoAtencionMedica().getConsultorio().getEspecialidad().getNombre());
        return consultaMedicaDto;
    }
}
