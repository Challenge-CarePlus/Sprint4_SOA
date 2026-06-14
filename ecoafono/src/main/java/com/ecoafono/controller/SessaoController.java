package com.ecoafono.controller;

import com.ecoafono.dto.DadosAgendamentoSessao;
import com.ecoafono.dto.DadosDetalhamentoSessao;
import com.ecoafono.service.SessaoService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/sessoes")
public class SessaoController {

    @Autowired
    private SessaoService sessaoService;

    @PostMapping
    @Transactional
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
    public ResponseEntity<DadosDetalhamentoSessao> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(sessaoService.buscarPorId(id));
    }
}