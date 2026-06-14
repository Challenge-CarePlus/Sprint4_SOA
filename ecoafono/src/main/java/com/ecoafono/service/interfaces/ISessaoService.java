package com.ecoafono.service.interfaces;

import com.ecoafono.dto.DadosAgendamentoSessao;
import com.ecoafono.dto.DadosDetalhamentoSessao;

public interface ISessaoService {

    DadosDetalhamentoSessao criar(DadosAgendamentoSessao dados);

    DadosDetalhamentoSessao buscarPorId(Long id);
}