package com.ecoafono.service;

import com.ecoafono.domain.model.Exercicio;
import com.ecoafono.dto.DadosCadastroExercicio;
import com.ecoafono.dto.DadosDetalhamentoExercicio;
import com.ecoafono.enums.FaixaEtaria;
import com.ecoafono.enums.Objetivo;
import com.ecoafono.repository.ExercicioRepository;
import com.ecoafono.service.interfaces.IExercicioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExercicioService implements IExercicioService {

    @Autowired
    private ExercicioRepository exercicioRepository;

    @Override
    public DadosDetalhamentoExercicio cadastrar(DadosCadastroExercicio dados) {
        Exercicio exercicio = new Exercicio(dados);
        exercicioRepository.save(exercicio);

        return new DadosDetalhamentoExercicio(exercicio);
    }

    @Override
    public List<DadosDetalhamentoExercicio> buscarExercicios(
            FaixaEtaria faixaEtaria,
            Objetivo objetivo
    ) {
        return exercicioRepository.buscarPorFiltros(faixaEtaria, objetivo)
                .stream()
                .map(DadosDetalhamentoExercicio::new)
                .toList();
    }

    @Override
    public List<Exercicio> buscarParaSessao(FaixaEtaria faixaEtaria, Objetivo objetivo) {
        return exercicioRepository.findByFaixaEtariaAndObjetivo(faixaEtaria, objetivo)
                .stream()
                .limit(10)
                .toList();
    }
}