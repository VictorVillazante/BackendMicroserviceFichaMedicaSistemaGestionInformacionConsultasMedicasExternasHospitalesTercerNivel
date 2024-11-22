package com.example.microserviciofichasmedicas.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.data.jpa.domain.Specification;

import com.example.microserviciofichasmedicas.model.ConsultaMedicaEntity;
import com.example.microserviciofichasmedicas.model.ConsultorioEntity;
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

    public static Specification<ConsultaMedicaEntity> obtenerConsultasMedicasPorIdMedico(int idMedico) {
        return (root,query,builder) -> {
            Join<ConsultaMedicaEntity, TurnosAtencionMedicaEntity> turnosAtencionMedicaJoin = root.join("turnoAtencionMedica");
            Join<TurnosAtencionMedicaEntity, UsuarioEntity> medicoJoin = turnosAtencionMedicaJoin.join("medico");
            Predicate predicate = builder.equal(medicoJoin.get("idUsuario"), idMedico);
            return predicate;
        };
    }
    public static Specification<ConsultaMedicaEntity> obtenerConsultasMedicasDeMedicoPorParametros(int idMedico,String min,String max) {
        try {
            return (root,query,builder) -> {
                LocalDate maxDate,minDate;
                DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                Join<ConsultaMedicaEntity, TurnosAtencionMedicaEntity> turnosAtencionMedicaJoin = root.join("turnoAtencionMedica");
                Join<TurnosAtencionMedicaEntity, UsuarioEntity> medicoJoin = turnosAtencionMedicaJoin.join("medico");
                Predicate predicadoFinal = builder.equal(medicoJoin.get("idUsuario"), idMedico);
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

    public static Specification<ConsultaMedicaEntity> obtenerConsultasMedicasDePacientePorParametros(int idPaciente, String min,
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
    
}
