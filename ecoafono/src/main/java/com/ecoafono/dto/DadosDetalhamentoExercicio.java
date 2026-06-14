package com.ecoafono.dto;

import com.ecoafono.domain.model.Exercicio;
import com.ecoafono.enums.FaixaEtaria;
import com.ecoafono.enums.Objetivo;

public record DadosDetalhamentoExercicio(
        Long id,
        String nome,
        String descricao,
        String instrucao,
        FaixaEtaria faixaEtaria,
        Objetivo objetivo
) {
    public DadosDetalhamentoExercicio(Exercicio exercicio) {
        this(
                exercicio.getId(),
                exercicio.getNome(),
                exercicio.getDescricao(),
                exercicio.getInstrucao(),
                exercicio.getFaixaEtaria(),
                exercicio.getObjetivo()
        );
    }
}