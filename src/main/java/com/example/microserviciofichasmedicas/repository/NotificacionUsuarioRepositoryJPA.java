package com.example.microserviciofichasmedicas.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.microserviciofichasmedicas.model.NotificacionUsuarioEntity;


@Repository
public interface NotificacionUsuarioRepositoryJPA extends JpaRepository<NotificacionUsuarioEntity, Integer> {
}