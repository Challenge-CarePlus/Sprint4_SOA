package com.ecoafono.service;

import com.ecoafono.domain.model.Usuario;
import com.ecoafono.dto.DadosAtualizacaoPreferencias;
import com.ecoafono.dto.DadosCadastroUsuario;
import com.ecoafono.dto.DadosDetalhamentoUsuario;
import com.ecoafono.dto.DadosEnderecoViaCep;
import com.ecoafono.exception.UsuarioNotFoundException;
import com.ecoafono.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ViaCepService viaCepService;

    @Transactional
    public DadosDetalhamentoUsuario criar(DadosCadastroUsuario dados) {
        if (usuarioRepository.existsByEmail(dados.email())) {
            throw new IllegalArgumentException("Já existe um usuário cadastrado com este email.");
        }

        DadosEnderecoViaCep endereco = viaCepService.buscarEndereco(dados.cep());

        Usuario usuario = new Usuario(
                dados,
                endereco.logradouro(),
                endereco.cidade(),
                endereco.estado()
        );

        usuarioRepository.save(usuario);

        return new DadosDetalhamentoUsuario(usuario);
    }

    public DadosDetalhamentoUsuario buscarPorId(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new UsuarioNotFoundException(id));

        return new DadosDetalhamentoUsuario(usuario);
    }

    @Transactional
    public DadosDetalhamentoUsuario atualizarPreferencias(
            Long id,
            DadosAtualizacaoPreferencias dados
    ) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new UsuarioNotFoundException(id));

        usuario.atualizarPreferencias(dados);

        return new DadosDetalhamentoUsuario(usuario);
    }
}