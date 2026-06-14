package com.ecoafono.controller;

import com.ecoafono.dto.DadosAtualizacaoPreferencias;
import com.ecoafono.dto.DadosCadastroUsuario;
import com.ecoafono.dto.DadosDetalhamentoUsuario;
import com.ecoafono.service.interfaces.IUsuarioService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.net.URI;

@RestController
@RequestMapping("/usuarios")
@Tag(name = "Usuários", description = "Endpoints para cadastro, consulta e atualização de preferências dos usuários.")
public class UsuarioController {

    @Autowired
    private IUsuarioService usuarioService;

    @PostMapping
    @Transactional
    @Operation(
            summary = "Cadastrar usuário",
            description = "Cadastra um novo usuário, consulta o endereço pelo CEP via ViaCEP e salva os dados no banco."
    )
    public ResponseEntity<DadosDetalhamentoUsuario> criar(
            @RequestBody @Valid DadosCadastroUsuario dados,
            UriComponentsBuilder uriBuilder
    ) {
        DadosDetalhamentoUsuario usuario = usuarioService.criar(dados);

        URI uri = uriBuilder.path("/usuarios/{id}")
                .buildAndExpand(usuario.id())
                .toUri();

        return ResponseEntity.created(uri).body(usuario);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Buscar usuário por ID",
            description = "Retorna os dados detalhados de um usuário cadastrado a partir do seu identificador."
    )
    public ResponseEntity<DadosDetalhamentoUsuario> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(usuarioService.buscarPorId(id));
    }

    @PutMapping("/{id}/preferencias")
    @Transactional
    @Operation(
            summary = "Atualizar preferências do usuário",
            description = "Atualiza a faixa etária e o objetivo do usuário para personalização dos exercícios."
    )
    public ResponseEntity<DadosDetalhamentoUsuario> atualizarPreferencias(
            @PathVariable Long id,
            @RequestBody @Valid DadosAtualizacaoPreferencias dados
    ) {
        return ResponseEntity.ok(usuarioService.atualizarPreferencias(id, dados));
    }
}