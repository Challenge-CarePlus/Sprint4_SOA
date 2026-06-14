package com.ecoafono.dto;

import com.ecoafono.enums.FaixaEtaria;
import com.ecoafono.enums.Objetivo;
import jakarta.validation.constraints.NotNull;

public record DadosAtualizacaoPreferencias(
        @NotNull
        FaixaEtaria faixaEtaria,

        @NotNull
        Objetivo objetivo
) {
}