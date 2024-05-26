package com.example.microserviciofichasmedicas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.example.microserviciofichasmedicas.model.FichasMedicasEntity;

public interface FichasMedicasRepositoryJPA extends org.springframework.data.jpa.repository.JpaRepository<FichasMedicasEntity, Integer>{
    List<FichasMedicasEntity> findByIdPaciente(int idPaciente);
    @Query(value="SELECT cm.codigo_ficha_medica,cm.numero_ficha,c.nombre,t.nombre,m.nombre,e.nombre FROM consultas_medicas cm"+
    " INNER JOIN turnos_atencion_medica tam ON tam.id_turno_atencion_medica =cm.id_turno_atencion_medica"+
    " INNER JOIN consultorios c ON tam.id_consultorio =c.id_consultorio"+
    " INNER JOIN turnos t  ON tam.id_turno =t.id_turno"+
    " INNER JOIN medicos m ON tam.id_medico =m.id_medico"+
    " INNER JOIN especialidades e ON c.id_especialidad=e.id_especialidad "+
    " WHERE cm.id_consulta_medica =?1",nativeQuery=true)
    List<Object> obtenerDetalleFichaMedica(int idFichaMedica);
}