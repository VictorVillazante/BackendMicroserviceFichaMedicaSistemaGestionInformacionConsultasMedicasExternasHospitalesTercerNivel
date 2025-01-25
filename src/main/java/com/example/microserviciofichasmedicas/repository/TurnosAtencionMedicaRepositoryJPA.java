package com.example.microserviciofichasmedicas.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.microserviciofichasmedicas.model.TurnosAtencionMedicaEntity;
import com.example.microserviciofichasmedicas.model.UsuarioEntity;

public interface TurnosAtencionMedicaRepositoryJPA extends JpaRepository<TurnosAtencionMedicaEntity, Integer>{

    Optional<TurnosAtencionMedicaEntity> findByIdTurnoAtencionMedicaAndDeletedAtIsNull(int idTurnoAtencionMedica);
    
}
