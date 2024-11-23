package com.example.microserviciofichasmedicas.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.microserviciofichasmedicas.model.TurnoEntity;


public interface TurnosRepositoryJPA extends JpaRepository<TurnoEntity, Integer> {
}