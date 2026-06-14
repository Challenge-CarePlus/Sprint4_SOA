package com.ecoafono.controller;

import com.ecoafono.dto.DadosDetalhamentoExercicio;
import com.ecoafono.enums.FaixaEtaria;
import com.ecoafono.enums.Objetivo;
import com.ecoafono.service.ExercicioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/exercicios")
public class ExercicioController {

    @Autowired
    private ExercicioService exercicioService;

    @GetMapping
    public ResponseEntity<List<DadosDetalhamentoExercicio>> buscarExercicios(
            @RequestParam(required = false) FaixaEtaria faixaEtaria,
            @RequestParam(required = false) Objetivo objetivo
    ) {
        return ResponseEntity.ok(exercicioService.buscarExercicios(faixaEtaria, objetivo));
    }
}