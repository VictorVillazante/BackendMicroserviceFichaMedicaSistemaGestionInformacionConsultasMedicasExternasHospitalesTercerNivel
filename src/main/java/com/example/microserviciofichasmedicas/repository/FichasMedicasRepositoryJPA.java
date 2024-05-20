package com.example.microserviciofichasmedicas.repository;

import java.util.List;

import com.example.microserviciofichasmedicas.model.FichasMedicasEntity;

public interface FichasMedicasRepositoryJPA extends org.springframework.data.jpa.repository.JpaRepository<FichasMedicasEntity, Integer>{
    List<FichasMedicasEntity> findByIdPaciente(int idPaciente);
}