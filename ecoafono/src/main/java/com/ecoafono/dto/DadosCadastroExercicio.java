package com.ecoafono.dto;

import com.ecoafono.enums.FaixaEtaria;
import com.ecoafono.enums.Objetivo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DadosCadastroExercicio(

        @NotBlank
        String nome,

        @NotBlank
        String descricao,

        @NotBlank
        String instrucao,

        @NotNull
        FaixaEtaria faixaEtaria,

        @NotNull
        Objetivo objetivo
) {
}