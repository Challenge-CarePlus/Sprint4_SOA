package com.ecoafono.service;

import com.ecoafono.domain.model.Exercicio;
import com.ecoafono.dto.DadosCadastroExercicio;
import com.ecoafono.dto.DadosDetalhamentoExercicio;
import com.ecoafono.enums.FaixaEtaria;
import com.ecoafono.enums.Objetivo;
import com.ecoafono.repository.ExercicioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ExercicioServiceTest {

    @Mock
    private ExercicioRepository exercicioRepository;

    @InjectMocks
    private ExercicioService exercicioService;

    @Test
    void deveCadastrarExercicio() {
        var dados = new DadosCadastroExercicio(
                "Respiração nasal",
                "Exercício para controle respiratório.",
                "Inspire pelo nariz e solte o ar lentamente.",
                FaixaEtaria.ADULTO,
                Objetivo.VOZ
        );

        var resultado = exercicioService.cadastrar(dados);

        assertNotNull(resultado);
        assertEquals("Respiração nasal", resultado.nome());
        assertEquals(FaixaEtaria.ADULTO, resultado.faixaEtaria());
        assertEquals(Objetivo.VOZ, resultado.objetivo());

        verify(exercicioRepository, times(1)).save(any(Exercicio.class));
    }

    @Test
    void deveBuscarExerciciosPorFiltros() {
        var exercicio = new Exercicio(
                new DadosCadastroExercicio(
                        "Sopro controlado",
                        "Exercício para treino de fala.",
                        "Sopre lentamente por 5 segundos.",
                        FaixaEtaria.CRIANCA,
                        Objetivo.FALA
                )
        );

        when(exercicioRepository.buscarPorFiltros(FaixaEtaria.CRIANCA, Objetivo.FALA))
                .thenReturn(List.of(exercicio));

        List<DadosDetalhamentoExercicio> resultado =
                exercicioService.buscarExercicios(FaixaEtaria.CRIANCA, Objetivo.FALA);

        assertEquals(1, resultado.size());
        assertEquals("Sopro controlado", resultado.get(0).nome());

        verify(exercicioRepository, times(1))
                .buscarPorFiltros(FaixaEtaria.CRIANCA, Objetivo.FALA);
    }
}