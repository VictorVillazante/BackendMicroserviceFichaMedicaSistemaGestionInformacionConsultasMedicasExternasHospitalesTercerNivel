package com.example.microserviciofichasmedicas.model.dtos;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Date;

import com.example.microserviciofichasmedicas.model.NotificacionEntity;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NotificacionDto {
    private int idNotificacion;
    private String titulo;
    private String descripcion;
    private Date createdAt;
    private Date updatedAt;
    private Date deletedAt;

    public NotificacionDto convertirNotificacionEntityANotificacionDto(NotificacionEntity notificacionEntity) {
        NotificacionDto notificacionDto = new NotificacionDto();
        notificacionDto.setIdNotificacion(notificacionEntity.getIdNotificacion());
        notificacionDto.setTitulo(notificacionEntity.getTitulo());
        notificacionDto.setDescripcion(notificacionEntity.getDescripcion());
        notificacionDto.setCreatedAt(notificacionEntity.getCreatedAt());
        notificacionDto.setUpdatedAt(notificacionEntity.getUpdatedAt());
        notificacionDto.setDeletedAt(notificacionEntity.getDeletedAt());
        return notificacionDto;
    }
}