package com.ecoafono.controller;

import com.ecoafono.dto.DadosAtualizacaoPreferencias;
import com.ecoafono.dto.DadosCadastroUsuario;
import com.ecoafono.dto.DadosDetalhamentoUsuario;
import com.ecoafono.service.UsuarioService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping
    @Transactional
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
    public ResponseEntity<DadosDetalhamentoUsuario> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(usuarioService.buscarPorId(id));
    }

    @PutMapping("/{id}/preferencias")
    @Transactional
    public ResponseEntity<DadosDetalhamentoUsuario> atualizarPreferencias(
            @PathVariable Long id,
            @RequestBody @Valid DadosAtualizacaoPreferencias dados
    ) {
        return ResponseEntity.ok(usuarioService.atualizarPreferencias(id, dados));
    }
}