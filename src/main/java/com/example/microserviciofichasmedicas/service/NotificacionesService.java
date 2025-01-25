package com.example.microserviciofichasmedicas.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.microserviciofichasmedicas.model.NotificacionEntity;
import com.example.microserviciofichasmedicas.model.dtos.NotificacionDto;
import com.example.microserviciofichasmedicas.repository.NotificacionesRepositoryJPA;

import jakarta.transaction.Transactional;

@Service
public class NotificacionesService {
    @Autowired
    private NotificacionesRepositoryJPA notificacionRepository;

    @Transactional
    public NotificacionDto crearNotificacion(NotificacionDto notificacionDto) {
        NotificacionEntity notificacionEntity = new NotificacionEntity();
        notificacionEntity.setTitulo(notificacionDto.getTitulo());
        notificacionEntity.setDescripcion(notificacionDto.getDescripcion());
        notificacionEntity = notificacionRepository.save(notificacionEntity);
        return new NotificacionDto().convertirNotificacionEntityANotificacionDto(notificacionEntity);
    }

    
}
