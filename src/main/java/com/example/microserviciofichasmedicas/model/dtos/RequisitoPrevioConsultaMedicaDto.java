package com.example.microserviciofichasmedicas.model.dtos;


import java.util.Date;

import com.example.microserviciofichasmedicas.model.RequisitoPrevioConsultaMedicaEntity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RequisitoPrevioConsultaMedicaDto {

    private int idRequisitoPrevioConsultaMedica;
    private String titulo;
    private String descripcion;
    private String keyArchivo;
    private String enlace;
    private int idConsultaMedica;
    private Date createdAt;
    private Date updatedAt;
    private Date deletedAt;

    public RequisitoPrevioConsultaMedicaDto convertirRequisitoPrevioConsultaMedicaEntityARequisitoPrevioConsultaMedicaDto(RequisitoPrevioConsultaMedicaEntity requisitoPrevioConsultaMedicaEntity) {
        RequisitoPrevioConsultaMedicaDto requisitoPrevioConsultaMedicaDto = new RequisitoPrevioConsultaMedicaDto();
        requisitoPrevioConsultaMedicaDto.setIdRequisitoPrevioConsultaMedica(requisitoPrevioConsultaMedicaEntity.getIdRequisitoPrevioConsultaMedica());
        requisitoPrevioConsultaMedicaDto.setTitulo(requisitoPrevioConsultaMedicaEntity.getTitulo());
        requisitoPrevioConsultaMedicaDto.setDescripcion(requisitoPrevioConsultaMedicaEntity.getDescripcion());
        requisitoPrevioConsultaMedicaDto.setKeyArchivo(requisitoPrevioConsultaMedicaEntity.getKeyArchivo());
        requisitoPrevioConsultaMedicaDto.setEnlace(requisitoPrevioConsultaMedicaEntity.getEnlace());
        requisitoPrevioConsultaMedicaDto.setIdConsultaMedica(requisitoPrevioConsultaMedicaEntity.getConsultaMedica().getIdConsultaMedica());
        requisitoPrevioConsultaMedicaDto.setCreatedAt(requisitoPrevioConsultaMedicaEntity.getCreatedAt());
        requisitoPrevioConsultaMedicaDto.setUpdatedAt(requisitoPrevioConsultaMedicaEntity.getUpdatedAt());
        requisitoPrevioConsultaMedicaDto.setDeletedAt(requisitoPrevioConsultaMedicaEntity.getDeletedAt());
        return requisitoPrevioConsultaMedicaDto;
    }
}