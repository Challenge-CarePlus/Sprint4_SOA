package com.ecoafono.dto;

import com.ecoafono.domain.model.Sessao;

import java.time.LocalDateTime;
import java.util.List;

public record DadosDetalhamentoSessao(
        Long id,
        Long idUsuario,
        String nomeUsuario,
        LocalDateTime data,
        List<DadosDetalhamentoExercicio> exercicios
) {
    public DadosDetalhamentoSessao(Sessao sessao) {
        this(
                sessao.getId(),
                sessao.getUsuario().getId(),
                sessao.getUsuario().getNome(),
                sessao.getData(),
                sessao.getExercicios().stream()
                        .map(DadosDetalhamentoExercicio::new)
                        .toList()
        );
    }
}