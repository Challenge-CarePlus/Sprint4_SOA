package com.ecoafono.service;

import com.ecoafono.domain.model.Exercicio;
import com.ecoafono.dto.DadosDetalhamentoExercicio;
import com.ecoafono.enums.FaixaEtaria;
import com.ecoafono.enums.Objetivo;
import com.ecoafono.repository.ExercicioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExercicioService {

    @Autowired
    private ExercicioRepository exercicioRepository;

    public List<DadosDetalhamentoExercicio> buscarExercicios(
            FaixaEtaria faixaEtaria,
            Objetivo objetivo
    ) {
        return exercicioRepository.buscarPorFiltros(faixaEtaria, objetivo)
                .stream()
                .map(DadosDetalhamentoExercicio::new)
                .toList();
    }

    public List<Exercicio> buscarParaSessao(FaixaEtaria faixaEtaria, Objetivo objetivo) {
        return exercicioRepository.findByFaixaEtariaAndObjetivo(faixaEtaria, objetivo)
                .stream()
                .limit(10)
                .toList();
    }
}