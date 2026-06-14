package com.ecoafono.service.interfaces;

import com.ecoafono.domain.model.Exercicio;
import com.ecoafono.dto.DadosCadastroExercicio;
import com.ecoafono.dto.DadosDetalhamentoExercicio;
import com.ecoafono.enums.FaixaEtaria;
import com.ecoafono.enums.Objetivo;

import java.util.List;

public interface IExercicioService {

    DadosDetalhamentoExercicio cadastrar(DadosCadastroExercicio dados);

    List<DadosDetalhamentoExercicio> buscarExercicios(
            FaixaEtaria faixaEtaria,
            Objetivo objetivo
    );

    List<Exercicio> buscarParaSessao(
            FaixaEtaria faixaEtaria,
            Objetivo objetivo
    );
}