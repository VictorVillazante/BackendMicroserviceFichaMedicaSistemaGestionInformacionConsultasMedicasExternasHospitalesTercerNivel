package com.example.microserviciofichasmedicas.util;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import org.springframework.data.jpa.domain.Specification;

import com.example.microserviciofichasmedicas.model.ConsultaMedicaEntity;
import com.example.microserviciofichasmedicas.model.ConsultorioEntity;
import com.example.microserviciofichasmedicas.model.EspecialidadesEntity;
import com.example.microserviciofichasmedicas.model.TurnoEntity;
import com.example.microserviciofichasmedicas.model.TurnosAtencionMedicaEntity;
import com.example.microserviciofichasmedicas.model.UsuarioEntity;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;

public class ConsultasMedicasSpecification {

    public static Specification<ConsultaMedicaEntity> greatherEqualThanFechaInicio(String min) {
        LocalDate minDate;
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        try {
            minDate = LocalDate.parse(min, formato);
            return (root, query, criteriaBuilder) -> {
                Join<ConsultaMedicaEntity, TurnosAtencionMedicaEntity> turnosAtencionMedicaJoin = root.join("turnoAtencionMedica");
                Predicate predicate = criteriaBuilder.greaterThanOrEqualTo(turnosAtencionMedicaJoin.get("fecha"), minDate);
                return predicate;

            };
        } catch (Exception e) {
            throw new RuntimeException("Fecha inválida");
        }
    }

    public static Specification<ConsultaMedicaEntity> lessEqualThanFechaFin(String max) {
        LocalDate maxDate;
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        try {
            maxDate = LocalDate.parse(max, formato);
            return (root, query, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root.get("fecha"), maxDate);
        } catch (Exception e) {
            throw new RuntimeException("Fecha inválida");
        }
    }

    public static Specification<ConsultaMedicaEntity> obtenerConsultasMedicasPorIdMedico(String idMedico) {
        return (root,query,builder) -> {
            Join<ConsultaMedicaEntity, TurnosAtencionMedicaEntity> turnosAtencionMedicaJoin = root.join("turnoAtencionMedica");
            Join<TurnosAtencionMedicaEntity, UsuarioEntity> medicoJoin = turnosAtencionMedicaJoin.join("medico");
            Predicate predicate = builder.equal(medicoJoin.get("idUsuario"), idMedico);
            return predicate;
        };
    }
    public static Specification<ConsultaMedicaEntity> obtenerConsultasMedicasDeMedicoPorParametros(String idMedico,String min,String max) {
        try {
            return (root,query,builder) -> {
                LocalDate maxDate,minDate;
                DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                Join<ConsultaMedicaEntity, TurnosAtencionMedicaEntity> turnosAtencionMedicaJoin = root.join("turnoAtencionMedica");
                Join<TurnosAtencionMedicaEntity, UsuarioEntity> medicoJoin = turnosAtencionMedicaJoin.join("medico");
                Predicate predicadoFinal = builder.equal(medicoJoin.get("idUsuario"), idMedico);
                //predicadoFinal = builder.and(predicadoFinal, builder.equal(root.get("estado"),"APROBADO ADM"));
                if (min != null) {
                    minDate = LocalDate.parse(min, formato);
                    Predicate predicadoFechaMin = builder.greaterThanOrEqualTo(turnosAtencionMedicaJoin.get("fecha"), minDate);
                    predicadoFinal = builder.and(predicadoFinal, predicadoFechaMin);
                }
                if (max != null) {
                    maxDate = LocalDate.parse(max, formato);
                    Predicate predicadoFechaMax = builder.lessThanOrEqualTo(turnosAtencionMedicaJoin.get("fecha"), maxDate);
                    predicadoFinal = builder.and(predicadoFinal, predicadoFechaMax);
                }
                return predicadoFinal;
            };
        } catch (Exception e) {
            throw new RuntimeException("Error obtener consultas medicas por parametros");
        }
        
    }

    public static Specification<ConsultaMedicaEntity> obtenerConsultasMedicasDePacientePorParametros(String idPaciente, String min,
            String max) {
                try {
                    return (root,query,builder) -> {
                        LocalDate maxDate,minDate;
                        DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                        Join<ConsultaMedicaEntity, TurnosAtencionMedicaEntity> turnosAtencionMedicaJoin = root.join("turnoAtencionMedica");
                        Join<TurnosAtencionMedicaEntity, UsuarioEntity> pacienteJoin = root.join("paciente");
                        Predicate predicadoFinal = builder.equal(pacienteJoin.get("idUsuario"), idPaciente);
                        
                        if (min != null) {
                            minDate = LocalDate.parse(min, formato);
                            Predicate predicadoFechaMin = builder.greaterThanOrEqualTo(turnosAtencionMedicaJoin.get("fecha"), minDate);
                            predicadoFinal = builder.and(predicadoFinal, predicadoFechaMin);
                        }
                        if (max != null) {
                            maxDate = LocalDate.parse(max, formato);
                            Predicate predicadoFechaMax = builder.lessThanOrEqualTo(turnosAtencionMedicaJoin.get("fecha"), maxDate);
                            predicadoFinal = builder.and(predicadoFinal, predicadoFechaMax);
                        }
                        return predicadoFinal;
                    };
                } catch (Exception e) {
                    throw new RuntimeException("Error obtener consultas medicas por parametros");
                }
    }

    public static Specification<ConsultaMedicaEntity> obtenerConsultasMedicas(Date fechaInicio, Date fechaFin,
            LocalTime hora,
            String nombreEspecialidad, String nombreMedico,
            String nombrePaciente) {
                try {
                    return (root,query,builder) -> {                        
                        Join<ConsultaMedicaEntity, TurnosAtencionMedicaEntity> turnosAtencionMedicaJoin = root.join("turnoAtencionMedica");
                        Join<TurnosAtencionMedicaEntity, TurnoEntity> turnosJoin = turnosAtencionMedicaJoin.join("turno");
                        Join<TurnosAtencionMedicaEntity, ConsultorioEntity> consultoriosJoin =turnosAtencionMedicaJoin.join("consultorio");
                        Join<ConsultorioEntity, EspecialidadesEntity> especialidadesJoin = consultoriosJoin.join("especialidad");
                        Join<TurnosAtencionMedicaEntity, UsuarioEntity> medicosJoin = turnosAtencionMedicaJoin.join("medico");
                        Join<ConsultaMedicaEntity, UsuarioEntity> pacientesJoin = root.join("paciente");

                        Predicate predicadoFinal = builder.conjunction();
                        if (fechaInicio!= null) {
                            Predicate predicadoFechaMin = builder.greaterThanOrEqualTo(turnosAtencionMedicaJoin.get("fecha"), fechaInicio);
                            predicadoFinal = builder.and(predicadoFinal, predicadoFechaMin);
                        }
                        if (fechaFin != null) {
                            Predicate predicadoFechaMax = builder.lessThanOrEqualTo(turnosAtencionMedicaJoin.get("fecha"), fechaFin);
                            predicadoFinal = builder.and(predicadoFinal, predicadoFechaMax);
                        }
                        if(hora!=null){
                            Predicate predicadoHoraMin = builder.and(
                                builder.greaterThanOrEqualTo(turnosJoin.get("horaInicio"), hora),
                                builder.lessThanOrEqualTo(turnosJoin.get("horaFin"), hora)
                            );                       
                            predicadoFinal = builder.and(predicadoFinal, predicadoHoraMin);
                        }
                        if (nombreEspecialidad != null) {
                            predicadoFinal = builder.and(predicadoFinal, builder.like(especialidadesJoin.get("nombre"), "%"+nombreEspecialidad+"%"));
                        }
                        if(nombrePaciente!=null){
                            Predicate predicadoNombres = builder.or(
                                builder.like(builder.lower(pacientesJoin.get("nombres")), "%" + nombrePaciente.toLowerCase() + "%"),
                                builder.like(builder.lower(pacientesJoin.get("apellidoPaterno")), "%" + nombrePaciente.toLowerCase() + "%"),
                                builder.like(builder.lower(pacientesJoin.get("apellidoMaterno")), "%" + nombrePaciente.toLowerCase() + "%")
                            );
                            predicadoFinal = builder.and(predicadoFinal, predicadoNombres);
                        }
                        if(nombreMedico!=null){
                            Predicate predicadoNombres = builder.or(
                                builder.like(builder.lower(medicosJoin.get("nombres")), "%" + nombreMedico.toLowerCase() + "%"),
                                builder.like(builder.lower(medicosJoin.get("apellidoPaterno")), "%" + nombreMedico.toLowerCase() + "%"),
                                builder.like(builder.lower(medicosJoin.get("apellidoMaterno")), "%" + nombreMedico.toLowerCase() + "%")
                            );
                            predicadoFinal = builder.and(predicadoFinal,predicadoNombres);
                        }
                        return predicadoFinal;
                    };
                } catch (Exception e) {
                    throw new RuntimeException("Error obtener consultas medicas por parametros");
                }
    }
    
}
