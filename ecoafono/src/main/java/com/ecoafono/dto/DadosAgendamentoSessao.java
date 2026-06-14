package com.ecoafono.dto;

import jakarta.validation.constraints.NotNull;

public record DadosAgendamentoSessao(
        @NotNull
        Long idUsuario
) {
}