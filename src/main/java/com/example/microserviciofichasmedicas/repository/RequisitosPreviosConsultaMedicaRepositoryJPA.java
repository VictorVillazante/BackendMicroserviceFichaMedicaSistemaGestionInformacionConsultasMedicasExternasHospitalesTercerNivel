package com.example.microserviciofichasmedicas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.microserviciofichasmedicas.model.ConsultaMedicaEntity;
import com.example.microserviciofichasmedicas.model.RequisitoPrevioConsultaMedicaEntity;

public interface RequisitosPreviosConsultaMedicaRepositoryJPA extends JpaRepository<RequisitoPrevioConsultaMedicaEntity, Integer> {

    List<RequisitoPrevioConsultaMedicaEntity> findAllByDeletedAtIsNull();

    List<RequisitoPrevioConsultaMedicaEntity> findAllByConsultaMedicaAndDeletedAtIsNull(
            ConsultaMedicaEntity consultaMedicaEntity);
}
