package com.ecoafono.service;

import com.ecoafono.domain.model.Exercicio;
import com.ecoafono.domain.model.Sessao;
import com.ecoafono.domain.model.Usuario;
import com.ecoafono.dto.DadosAgendamentoSessao;
import com.ecoafono.dto.DadosDetalhamentoSessao;
import com.ecoafono.exception.UsuarioNotFoundException;
import com.ecoafono.repository.SessaoRepository;
import com.ecoafono.repository.UsuarioRepository;
import com.ecoafono.service.interfaces.IExercicioService;
import com.ecoafono.service.interfaces.ISessaoService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SessaoService implements ISessaoService {

    @Autowired
    private SessaoRepository sessaoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private IExercicioService exercicioService;

    @Override
    @Transactional
    public DadosDetalhamentoSessao criar(DadosAgendamentoSessao dados) {
        Usuario usuario = usuarioRepository.findById(dados.idUsuario())
                .orElseThrow(() -> new UsuarioNotFoundException(dados.idUsuario()));

        if (usuario.getFaixaEtaria() == null || usuario.getObjetivo() == null) {
            throw new IllegalStateException("O usuário precisa ter faixa etária e objetivo definidos.");
        }

        List<Exercicio> exercicios = exercicioService.buscarParaSessao(
                usuario.getFaixaEtaria(),
                usuario.getObjetivo()
        );

        if (exercicios.isEmpty()) {
            throw new IllegalStateException("Nenhum exercício encontrado para este perfil.");
        }

        Sessao sessao = new Sessao(usuario, exercicios);

        sessaoRepository.save(sessao);

        return new DadosDetalhamentoSessao(sessao);
    }

    @Override
    public DadosDetalhamentoSessao buscarPorId(Long id) {
        Sessao sessao = sessaoRepository.findByIdWithExercicios(id)
                .orElseThrow(() -> new RuntimeException("Sessão não encontrada."));

        return new DadosDetalhamentoSessao(sessao);
    }
}