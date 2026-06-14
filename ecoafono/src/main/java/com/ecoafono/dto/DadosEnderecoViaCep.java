package com.ecoafono.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record DadosEnderecoViaCep(
        String logradouro,

        @JsonProperty("localidade")
        String cidade,

        @JsonProperty("uf")
        String estado,

        Boolean erro
) {
    public boolean isCepInvalido() {
        return Boolean.TRUE.equals(erro);
    }
}