package com.ecoafono.dto;

import com.ecoafono.domain.model.Usuario;
import com.ecoafono.enums.FaixaEtaria;
import com.ecoafono.enums.Objetivo;

public record DadosDetalhamentoUsuario(
        Long id,
        String nome,
        String email,
        String cep,
        String logradouro,
        String cidade,
        String estado,
        FaixaEtaria faixaEtaria,
        Objetivo objetivo
) {
    public DadosDetalhamentoUsuario(Usuario usuario) {
        this(
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getCep(),
                usuario.getLogradouro(),
                usuario.getCidade(),
                usuario.getEstado(),
                usuario.getFaixaEtaria(),
                usuario.getObjetivo()
        );
    }
}