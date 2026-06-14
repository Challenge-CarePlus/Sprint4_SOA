package com.ecoafono.controller;

import com.ecoafono.dto.DadosCadastroExercicio;
import com.ecoafono.dto.DadosDetalhamentoExercicio;
import com.ecoafono.enums.FaixaEtaria;
import com.ecoafono.enums.Objetivo;
import com.ecoafono.service.interfaces.IExercicioService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/exercicios")
@Tag(name = "Exercícios", description = "Endpoints para cadastro e consulta de exercícios fonoaudiológicos.")
public class ExercicioController {

    @Autowired
    private IExercicioService exercicioService;

    @PostMapping
    @Transactional
    @Operation(
            summary = "Cadastrar exercício",
            description = "Cadastra um novo exercício fonoaudiológico no sistema."
    )
    public ResponseEntity<DadosDetalhamentoExercicio> cadastrar(
            @RequestBody @Valid DadosCadastroExercicio dados,
            UriComponentsBuilder uriBuilder
    ) {
        DadosDetalhamentoExercicio exercicio = exercicioService.cadastrar(dados);

        URI uri = uriBuilder.path("/exercicios/{id}")
                .buildAndExpand(exercicio.id())
                .toUri();

        return ResponseEntity.created(uri).body(exercicio);
    }

    @GetMapping
    @Operation(
            summary = "Listar exercícios",
            description = "Lista exercícios cadastrados com filtros opcionais por faixa etária e objetivo."
    )
    public ResponseEntity<List<DadosDetalhamentoExercicio>> buscarExercicios(
            @RequestParam(required = false) FaixaEtaria faixaEtaria,
            @RequestParam(required = false) Objetivo objetivo
    ) {
        return ResponseEntity.ok(exercicioService.buscarExercicios(faixaEtaria, objetivo));
    }
}