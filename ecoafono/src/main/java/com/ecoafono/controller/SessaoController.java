package com.ecoafono.controller;

import com.ecoafono.dto.DadosAgendamentoSessao;
import com.ecoafono.dto.DadosDetalhamentoSessao;
import com.ecoafono.service.interfaces.ISessaoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.net.URI;

@RestController
@RequestMapping("/sessoes")
@Tag(name = "Sessões", description = "Endpoints para criação e consulta de sessões fonoaudiológicas.")
public class SessaoController {

    @Autowired
    private ISessaoService sessaoService;

    @PostMapping
    @Operation(
            summary = "Criar sessão",
            description = "Cria uma sessão para um usuário, selecionando exercícios compatíveis com sua faixa etária e objetivo."
    )
    public ResponseEntity<DadosDetalhamentoSessao> criar(
            @RequestBody @Valid DadosAgendamentoSessao dados,
            UriComponentsBuilder uriBuilder
    ) {
        DadosDetalhamentoSessao sessao = sessaoService.criar(dados);

        URI uri = uriBuilder.path("/sessoes/{id}")
                .buildAndExpand(sessao.id())
                .toUri();

        return ResponseEntity.created(uri).body(sessao);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Buscar sessão por ID",
            description = "Retorna os dados de uma sessão cadastrada, incluindo os exercícios associados."
    )
    public ResponseEntity<DadosDetalhamentoSessao> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(sessaoService.buscarPorId(id));
    }
}