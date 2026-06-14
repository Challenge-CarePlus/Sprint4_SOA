package com.ecoafono.repository;

import com.ecoafono.domain.model.UsuarioSistema;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface UsuarioSistemaRepository extends JpaRepository<UsuarioSistema, Long> {

    UserDetails findByLogin(String login);
}