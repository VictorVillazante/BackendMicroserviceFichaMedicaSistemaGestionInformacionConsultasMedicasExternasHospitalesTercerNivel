package com.example.microserviciofichasmedicas.repository;

import java.util.List;
import java.util.Optional;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.microserviciofichasmedicas.model.ConsultorioEntity;


public interface ConsultoriosRepositoryJPA extends JpaRepository<ConsultorioEntity, Integer> {
    @Query(value="SELECT c.id_consultorio,c.nombre,c.direccion,c.equipamiento,e.nombre from consultorios c "+
    "INNER JOIN especialidades e ON c.id_especialidad  = e.id_especialidad ",nativeQuery=true)
    List<Object> obtenerConsultorios();
    List<ConsultorioEntity> findAllByDeletedAtIsNull();
    Optional<ConsultorioEntity> findByIdConsultorioAndDeletedAtIsNull(int idConsultorio);
}