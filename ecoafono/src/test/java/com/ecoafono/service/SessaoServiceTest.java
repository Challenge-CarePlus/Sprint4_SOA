package com.ecoafono.service;

import com.ecoafono.domain.model.Exercicio;
import com.ecoafono.domain.model.Sessao;
import com.ecoafono.domain.model.Usuario;
import com.ecoafono.dto.DadosAgendamentoSessao;
import com.ecoafono.dto.DadosDetalhamentoSessao;
import com.ecoafono.enums.FaixaEtaria;
import com.ecoafono.enums.Objetivo;
import com.ecoafono.repository.SessaoRepository;
import com.ecoafono.repository.UsuarioRepository;
import com.ecoafono.service.interfaces.IExercicioService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SessaoServiceTest {

    @Mock
    private SessaoRepository sessaoRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private IExercicioService exercicioService;

    @InjectMocks
    private SessaoService sessaoService;

    @Test
    void deveCriarSessaoComExerciciosDoPerfilDoUsuario() {
        var usuario = new Usuario(
                1L,
                "Isabela",
                "isabela@email.com",
                "06010000",
                "Rua Teste",
                "Osasco",
                "SP",
                FaixaEtaria.ADULTO,
                Objetivo.VOZ
        );

        var exercicio = new Exercicio(
                1L,
                "Respiração nasal",
                "Exercício para controle respiratório.",
                "Inspire pelo nariz e solte lentamente.",
                FaixaEtaria.ADULTO,
                Objetivo.VOZ
        );

        var dados = new DadosAgendamentoSessao(1L);

        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        when(exercicioService.buscarParaSessao(FaixaEtaria.ADULTO, Objetivo.VOZ))
                .thenReturn(List.of(exercicio));

        DadosDetalhamentoSessao resultado = sessaoService.criar(dados);

        assertNotNull(resultado);
        assertEquals(1L, resultado.idUsuario());
        assertEquals("Isabela", resultado.nomeUsuario());
        assertEquals(1, resultado.exercicios().size());
        assertEquals("Respiração nasal", resultado.exercicios().get(0).nome());

        verify(usuarioRepository, times(1)).findById(1L);
        verify(exercicioService, times(1))
                .buscarParaSessao(FaixaEtaria.ADULTO, Objetivo.VOZ);
        verify(sessaoRepository, times(1)).save(any(Sessao.class));
    }

    @Test
    void naoDeveCriarSessaoQuandoUsuarioNaoTemPreferenciasDefinidas() {
        var usuario = new Usuario(
                1L,
                "Isabela",
                "isabela@email.com",
                "06010000",
                "Rua Teste",
                "Osasco",
                "SP",
                null,
                null
        );

        var dados = new DadosAgendamentoSessao(1L);

        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));

        assertThrows(IllegalStateException.class, () -> sessaoService.criar(dados));

        verify(usuarioRepository, times(1)).findById(1L);
        verify(exercicioService, never()).buscarParaSessao(any(), any());
        verify(sessaoRepository, never()).save(any(Sessao.class));
    }

    @Test
    void naoDeveCriarSessaoQuandoNaoExistemExerciciosParaPerfil() {
        var usuario = new Usuario(
                1L,
                "Isabela",
                "isabela@email.com",
                "06010000",
                "Rua Teste",
                "Osasco",
                "SP",
                FaixaEtaria.ADULTO,
                Objetivo.VOZ
        );

        var dados = new DadosAgendamentoSessao(1L);

        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        when(exercicioService.buscarParaSessao(FaixaEtaria.ADULTO, Objetivo.VOZ))
                .thenReturn(List.of());

        assertThrows(IllegalStateException.class, () -> sessaoService.criar(dados));

        verify(usuarioRepository, times(1)).findById(1L);
        verify(exercicioService, times(1))
                .buscarParaSessao(FaixaEtaria.ADULTO, Objetivo.VOZ);
        verify(sessaoRepository, never()).save(any(Sessao.class));
    }

    @Test
    void deveBuscarSessaoPorId() {
        var usuario = new Usuario(
                1L,
                "Isabela",
                "isabela@email.com",
                "06010000",
                "Rua Teste",
                "Osasco",
                "SP",
                FaixaEtaria.ADULTO,
                Objetivo.VOZ
        );

        var exercicio = new Exercicio(
                1L,
                "Respiração nasal",
                "Exercício para controle respiratório.",
                "Inspire pelo nariz e solte lentamente.",
                FaixaEtaria.ADULTO,
                Objetivo.VOZ
        );

        var sessao = new Sessao(
                1L,
                usuario,
                LocalDateTime.now(),
                List.of(exercicio)
        );

        when(sessaoRepository.findByIdWithExercicios(1L))
                .thenReturn(Optional.of(sessao));

        DadosDetalhamentoSessao resultado = sessaoService.buscarPorId(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.id());
        assertEquals(1L, resultado.idUsuario());
        assertEquals("Isabela", resultado.nomeUsuario());
        assertEquals(1, resultado.exercicios().size());

        verify(sessaoRepository, times(1)).findByIdWithExercicios(1L);
    }
}