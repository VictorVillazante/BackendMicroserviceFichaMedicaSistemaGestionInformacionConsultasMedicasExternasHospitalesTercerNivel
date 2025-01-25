package com.example.microserviciofichasmedicas.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.microserviciofichasmedicas.model.NotificacionUsuarioEntity;
import com.example.microserviciofichasmedicas.model.dtos.NotificacionUsuarioDto;
import com.example.microserviciofichasmedicas.repository.NotificacionUsuarioRepositoryJPA;
import com.example.microserviciofichasmedicas.repository.NotificacionesRepositoryJPA;
import com.example.microserviciofichasmedicas.repository.UsuarioRepositoryJPA;

import jakarta.transaction.Transactional;

@Service
public class NotificacionesUsuariosService {

    @Autowired
    private NotificacionUsuarioRepositoryJPA notificacionUsuarioRepository;

    @Autowired
    private NotificacionesRepositoryJPA notificacionRepository;

    @Autowired
    private UsuarioRepositoryJPA usuarioRepository;

    @Transactional
    public NotificacionUsuarioDto crearNotificacionUsuario(NotificacionUsuarioDto notificacionUsuarioDto) throws Exception {
        NotificacionUsuarioEntity notificacionUsuarioEntity = new NotificacionUsuarioEntity();
        notificacionUsuarioEntity.setNotificacion(notificacionRepository.findById(notificacionUsuarioDto.getIdNotificacion()).orElseThrow(()->new Exception("Usuario no encontrado")));
        notificacionUsuarioEntity.setUsuario(usuarioRepository.findById(notificacionUsuarioDto.getIdUsuario()).orElseThrow(()->new Exception("Usuario no encontrado")));
        notificacionUsuarioEntity.setLeido(notificacionUsuarioDto.getLeido());
        notificacionUsuarioEntity = notificacionUsuarioRepository.save(notificacionUsuarioEntity);

        return new NotificacionUsuarioDto().convertirNotificacionUsuarioEntityANotificacionUsuarioDto(notificacionUsuarioEntity);
    }
}
