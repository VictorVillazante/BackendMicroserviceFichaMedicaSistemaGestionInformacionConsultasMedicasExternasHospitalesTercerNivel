package com.example.microserviciofichasmedicas.model.dtos;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

import com.example.microserviciofichasmedicas.model.NotificacionUsuarioEntity;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NotificacionUsuarioDto {
    private int idNotificacionUsuario;
    private int idNotificacion;
    private String idUsuario;
    private Boolean leido;
    private Date createdAt;
    private Date updatedAt;
    private Date deletedAt;

    public NotificacionUsuarioDto convertirNotificacionUsuarioEntityANotificacionUsuarioDto(NotificacionUsuarioEntity notificacionUsuarioEntity) {
        NotificacionUsuarioDto notificacionUsuarioDto = new NotificacionUsuarioDto();
        notificacionUsuarioDto.setIdNotificacionUsuario(notificacionUsuarioEntity.getIdNotificacionUsuario());
        notificacionUsuarioDto.setIdNotificacion(notificacionUsuarioEntity.getNotificacion().getIdNotificacion());
        notificacionUsuarioDto.setIdUsuario(notificacionUsuarioEntity.getUsuario().getIdUsuario());
        notificacionUsuarioDto.setLeido(notificacionUsuarioEntity.getLeido());
        notificacionUsuarioDto.setCreatedAt(notificacionUsuarioEntity.getCreatedAt());
        notificacionUsuarioDto.setUpdatedAt(notificacionUsuarioEntity.getUpdatedAt());
        notificacionUsuarioDto.setDeletedAt(notificacionUsuarioEntity.getDeletedAt());
        return notificacionUsuarioDto;
    }
}