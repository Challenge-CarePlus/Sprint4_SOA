package com.ecoafono.repository;

import com.ecoafono.domain.model.Exercicio;
import com.ecoafono.enums.FaixaEtaria;
import com.ecoafono.enums.Objetivo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ExercicioRepository extends JpaRepository<Exercicio, Long> {

    @Query("""
            SELECT e FROM Exercicio e
            WHERE (:faixaEtaria IS NULL OR e.faixaEtaria = :faixaEtaria)
            AND (:objetivo IS NULL OR e.objetivo = :objetivo)
            ORDER BY e.nome
            """)
    List<Exercicio> buscarPorFiltros(
            @Param("faixaEtaria") FaixaEtaria faixaEtaria,
            @Param("objetivo") Objetivo objetivo
    );

    List<Exercicio> findByFaixaEtariaAndObjetivo(FaixaEtaria faixaEtaria, Objetivo objetivo);
}