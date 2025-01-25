package com.example.microserviciofichasmedicas.repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.microserviciofichasmedicas.model.ConsultaMedicaEntity;
import com.example.microserviciofichasmedicas.model.TurnosAtencionMedicaEntity;
import com.example.microserviciofichasmedicas.model.UsuarioEntity;

public interface ConsultaMedicaRepositoryJPA extends org.springframework.data.jpa.repository.JpaRepository<ConsultaMedicaEntity, Integer>,JpaSpecificationExecutor<ConsultaMedicaEntity> {
    // List<FichasMedicasEntity> findByIdPaciente(int idPaciente);
    List<ConsultaMedicaEntity> findByPaciente(UsuarioEntity paciente);
    @Query(value="SELECT cm FROM ConsultaMedicaEntity cm "+
    "JOIN cm.turnoAtencionMedica tam "+
    "JOIN tam.medico m "+
    "WHERE m.idUsuario = ?1")
    List<ConsultaMedicaEntity> obtenerFichasMedicasPorIdMedico(int idMedico);

     // @Query(value="SELECT cm.codigo_ficha_medica,cm.numero_ficha,c.nombre,t.nombre,m.nombres,e.nombre FROM consultas_medicas cm"+
    // " INNER JOIN turnos_atencion_medica tam ON tam.id_turno_atencion_medica =cm.id_turno_atencion_medica"+
    // " INNER JOIN consultorios c ON tam.id_consultorio =c.id_consultorio"+
    // " INNER JOIN turnos t  ON tam.id_turno =t.id_turno"+
    // " INNER JOIN usuarios m ON tam.id_medico =m.id_usuario"+
    // " INNER JOIN especialidades e ON c.id_especialidad=e.id_especialidad "+
    // " WHERE cm.id_consulta_medica =?1",nativeQuery=true)
    // List<Object> obtenerDetalleFichaMedica(int idFichaMedica);
      // List<FichasMedicasEntity> findByIdPaciente(int idPaciente);
    // @Query(value="SELECT cm.codigo_ficha_medica,cm.numero_ficha,c.nombre,t.nombre,m.nombres,e.nombre FROM consultas_medicas cm"+
    // " INNER JOIN turnos_atencion_medica tam ON tam.id_turno_atencion_medica =cm.id_turno_atencion_medica"+
    // " INNER JOIN consultorios c ON tam.id_consultorio =c.id_consultorio"+
    // " INNER JOIN turnos t  ON tam.id_turno =t.id_turno"+
    // " INNER JOIN usuarios m ON tam.id_medico =m.id_usuario"+
    // " INNER JOIN especialidades e ON c.id_especialidad=e.id_especialidad "+
    // " WHERE cm.id_consulta_medica =?1",nativeQuery=true)
    // List<Object> obtenerDetalleFichaMedica(int idFichaMedica);
    // @Query(value="SELECT cm.id_consulta_medica,cm.codigo_ficha_medica,cm.numero_ficha,c.nombre,t.nombre,e.nombre,CONCAT(m.nombres,' ',m.apellido_paterno,' ',m.apellido_materno),tam.fecha FROM consultas_medicas cm "+
    // "INNER JOIN turnos_atencion_medica tam ON tam.id_turno_atencion_medica =cm.id_turno_atencion_medica "+
    // "INNER JOIN turnos t  ON tam.id_turno =t.id_turno "+
    // "INNER JOIN usuarios m ON tam.id_medico =m.id_usuario "+
    // "INNER JOIN consultorios c ON tam.id_consultorio =c.id_consultorio "+
    // "INNER JOIN especialidades e ON c.id_especialidad  =e.id_especialidad  "+
    // "INNER JOIN usuarios p ON p.id_usuario = cm.id_paciente "+
    // "WHERE tam.id_medico = ?1"
    // ,nativeQuery=true)
    // List<Object> obtenerFichasMedicasPorIdMedico(int idMedico);
    // @Query(value="SELECT cm.id_consulta_medica,cm.codigo_ficha_medica,cm.numero_ficha,c.nombre,t.nombre,e.nombre,CONCAT(p.nombres,' ',p.apellido_paterno,' ',p.apellido_materno),tam.fecha FROM consultas_medicas cm "+
    // "INNER JOIN turnos_atencion_medica tam ON tam.id_turno_atencion_medica =cm.id_turno_atencion_medica "+
    // "INNER JOIN turnos t  ON tam.id_turno =t.id_turno "+
    // "INNER JOIN usuarios m ON tam.id_medico =m.id_usuario "+
    // "INNER JOIN consultorios c ON tam.id_consultorio =c.id_consultorio "+
    // "INNER JOIN especialidades e ON c.id_especialidad  =e.id_especialidad  "+
    // "INNER JOIN usuarios p ON p.id_usuario = cm.id_paciente "+
    // "WHERE cm.id_paciente = ?1"
    // ,nativeQuery=true)
    // List<Object> obtenerFichasMedicasPorIdPaciente(int idPaciente);
    // @Query(value="SELECT cm.id_consulta_medica,cm.codigo_ficha_medica,cm.numero_ficha,c.nombre,t.nombre,e.nombre,CONCAT(p.nombres,' ',p.apellido_paterno,' ',p.apellido_materno),tam.fecha FROM consultas_medicas cm "+
    // "INNER JOIN turnos_atencion_medica tam ON tam.id_turno_atencion_medica =cm.id_turno_atencion_medica "+
    // "INNER JOIN turnos t  ON tam.id_turno =t.id_turno "+
    // "INNER JOIN usuarios m ON tam.id_medico =m.id_usuario "+
    // "INNER JOIN consultorios c ON tam.id_consultorio =c.id_consultorio "+
    // "INNER JOIN especialidades e ON c.id_especialidad  =e.id_especialidad  "+
    // "INNER JOIN usuarios p ON p.id_usuario = cm.id_paciente "+
    // "WHERE cm.id_paciente = ?1"
    // ,nativeQuery=true)
    // List<ConsultaMedicaEntity> obtenerFichasMedicasPorIdPaciente(int idPaciente);
    @Query(value="SELECT cm FROM ConsultaMedicaEntity cm "+
    "JOIN cm.turnoAtencionMedica tam "+
    "WHERE tam.idTurnoAtencionMedica = ?1 "+
    "AND cm.estado = 'EN CURSO'")
    ConsultaMedicaEntity obtenerConsultaEnCurso(int idTurnoAtencionMedica);
    @Query(value="SELECT cm FROM ConsultaMedicaEntity cm "+
    "JOIN cm.turnoAtencionMedica tam "+
    "JOIN cm.paciente p "+
    "WHERE p.idUsuario = ?1 "+
    "AND cm.estado = 'PENDIENTE APROBACION' "+
    "AND tam.idTurnoAtencionMedica = ?2")
    ConsultaMedicaEntity obtenerConsultasNoAprobadas(String idPaciente,int  idTurnoAtencionMedica);
    @Query(value = "UPDATE ConsultaMedicaEntity cm SET cm.estado = 'RECHAZADA' WHERE cm.estado = 'PENDIENTE APROBACION' AND cm.turnoAtencionMedica.fecha < ?1")
    void rechazarPeticionesFueraFecha(LocalDate fecha);

    @Modifying
    @Query(value = "UPDATE consultas_medicas SET deleted_at = ?2 WHERE id_turno_atencion_medica = ?1 AND deleted_at IS NULL", nativeQuery = true)
    void markAsDeletedAllConsultasMedicasFromTurnoAtencionMedica(int idTurnoAtencionMedica, Date date);
    
    Optional<ConsultaMedicaEntity> findByIdConsultaMedicaAndDeletedAtIsNull(int idConsultaMedica);
    List<ConsultaMedicaEntity> findAllByTurnoAtencionMedicaAndDeletedAtIsNull(TurnosAtencionMedicaEntity turnosAtencionMedicaEntity);
}