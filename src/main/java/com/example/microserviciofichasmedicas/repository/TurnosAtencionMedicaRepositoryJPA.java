package com.example.microserviciofichasmedicas.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.microserviciofichasmedicas.model.TurnosAtencionMedicaEntity;

public interface TurnosAtencionMedicaRepositoryJPA extends JpaRepository<TurnosAtencionMedicaEntity, Integer>{
    
}
