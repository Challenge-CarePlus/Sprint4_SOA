package com.ecoafono.service.interfaces;

import com.ecoafono.dto.DadosAtualizacaoPreferencias;
import com.ecoafono.dto.DadosCadastroUsuario;
import com.ecoafono.dto.DadosDetalhamentoUsuario;

public interface IUsuarioService {

    DadosDetalhamentoUsuario criar(DadosCadastroUsuario dados);

    DadosDetalhamentoUsuario buscarPorId(Long id);

    DadosDetalhamentoUsuario atualizarPreferencias(
            Long id,
            DadosAtualizacaoPreferencias dados
    );
}