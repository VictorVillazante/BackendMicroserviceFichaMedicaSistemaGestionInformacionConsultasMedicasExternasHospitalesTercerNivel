package com.example.microserviciofichasmedicas.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.microserviciofichasmedicas.model.ConsultorioEntity;
import com.example.microserviciofichasmedicas.model.TurnoEntity;


public interface TurnosRepositoryJPA extends JpaRepository<TurnoEntity, Integer> {

    Optional<TurnoEntity> findByIdTurnoAndDeletedAtIsNull(Integer idTurno);
}