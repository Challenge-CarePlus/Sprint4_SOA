package com.ecoafono.service;

import com.ecoafono.domain.model.Usuario;
import com.ecoafono.dto.*;
import com.ecoafono.enums.FaixaEtaria;
import com.ecoafono.enums.Objetivo;
import com.ecoafono.repository.UsuarioRepository;
import com.ecoafono.service.interfaces.IViaCepService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private IViaCepService viaCepService;

    @InjectMocks
    private UsuarioService usuarioService;

    @Test
    void deveCriarUsuarioComEnderecoDoViaCep() {
        var dados = new DadosCadastroUsuario(
                "Isabela",
                "isabela@email.com",
                "06010000",
                FaixaEtaria.ADULTO,
                Objetivo.VOZ
        );

        var endereco = new DadosEnderecoViaCep(
                "Rua Teste",
                "Osasco",
                "SP",
                false
        );

        when(usuarioRepository.existsByEmail(dados.email())).thenReturn(false);
        when(viaCepService.buscarEndereco(dados.cep())).thenReturn(endereco);

        DadosDetalhamentoUsuario resultado = usuarioService.criar(dados);

        assertNotNull(resultado);
        assertEquals("Isabela", resultado.nome());
        assertEquals("isabela@email.com", resultado.email());
        assertEquals("Rua Teste", resultado.logradouro());
        assertEquals("Osasco", resultado.cidade());
        assertEquals("SP", resultado.estado());
        assertEquals(FaixaEtaria.ADULTO, resultado.faixaEtaria());
        assertEquals(Objetivo.VOZ, resultado.objetivo());

        verify(usuarioRepository, times(1)).existsByEmail(dados.email());
        verify(viaCepService, times(1)).buscarEndereco(dados.cep());
        verify(usuarioRepository, times(1)).save(any(Usuario.class));
    }

    @Test
    void naoDeveCriarUsuarioComEmailDuplicado() {
        var dados = new DadosCadastroUsuario(
                "Isabela",
                "isabela@email.com",
                "06010000",
                FaixaEtaria.ADULTO,
                Objetivo.VOZ
        );

        when(usuarioRepository.existsByEmail(dados.email())).thenReturn(true);

        assertThrows(IllegalArgumentException.class, () -> usuarioService.criar(dados));

        verify(usuarioRepository, times(1)).existsByEmail(dados.email());
        verify(viaCepService, never()).buscarEndereco(anyString());
        verify(usuarioRepository, never()).save(any(Usuario.class));
    }

    @Test
    void deveBuscarUsuarioPorId() {
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

        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));

        DadosDetalhamentoUsuario resultado = usuarioService.buscarPorId(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.id());
        assertEquals("Isabela", resultado.nome());
        assertEquals(Objetivo.VOZ, resultado.objetivo());

        verify(usuarioRepository, times(1)).findById(1L);
    }

    @Test
    void deveAtualizarPreferenciasDoUsuario() {
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

        var dadosAtualizacao = new DadosAtualizacaoPreferencias(
                FaixaEtaria.IDOSO,
                Objetivo.COMUNICACAO
        );

        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));

        DadosDetalhamentoUsuario resultado =
                usuarioService.atualizarPreferencias(1L, dadosAtualizacao);

        assertEquals(FaixaEtaria.IDOSO, resultado.faixaEtaria());
        assertEquals(Objetivo.COMUNICACAO, resultado.objetivo());

        verify(usuarioRepository, times(1)).findById(1L);
    }
}