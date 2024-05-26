package com.example.microserviciofichasmedicas.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.microserviciofichasmedicas.model.PacienteEntity;


public interface PacientesRepositoryJPA extends JpaRepository<PacienteEntity, Integer>{
    Optional<PacienteEntity> findByEmail(String email);

}
