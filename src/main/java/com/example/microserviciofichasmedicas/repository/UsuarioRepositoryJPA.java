package com.example.microserviciofichasmedicas.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.microserviciofichasmedicas.model.UsuarioEntity;

public interface UsuarioRepositoryJPA extends JpaRepository<UsuarioEntity, String>{
    Optional<UsuarioEntity> findByEmail(String email);
    Optional<UsuarioEntity> findByIdUsuarioAndDeletedAtIsNull(String idUsuario);

}
