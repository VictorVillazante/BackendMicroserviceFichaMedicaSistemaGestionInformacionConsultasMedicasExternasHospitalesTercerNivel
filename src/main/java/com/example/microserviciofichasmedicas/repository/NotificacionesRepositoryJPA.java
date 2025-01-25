package com.example.microserviciofichasmedicas.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.microserviciofichasmedicas.model.NotificacionEntity;


@Repository
public interface NotificacionesRepositoryJPA extends JpaRepository<NotificacionEntity, Integer> {    
}
