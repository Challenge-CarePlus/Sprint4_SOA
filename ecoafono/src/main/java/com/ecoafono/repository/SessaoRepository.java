package com.ecoafono.repository;

import com.ecoafono.domain.model.Sessao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface SessaoRepository extends JpaRepository<Sessao, Long> {

    @Query("""
            SELECT s FROM Sessao s
            LEFT JOIN FETCH s.exercicios
            WHERE s.id = :id
            """)
    Optional<Sessao> findByIdWithExercicios(@Param("id") Long id);
}