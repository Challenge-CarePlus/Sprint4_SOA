package com.ecoafono.domain.model;

import com.ecoafono.dto.DadosCadastroExercicio;
import com.ecoafono.enums.FaixaEtaria;
import com.ecoafono.enums.Objetivo;
import jakarta.persistence.*;
import lombok.*;

@Table(name = "exercicios")
@Entity(name = "Exercicio")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Exercicio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String descricao;
    private String instrucao;

    @Enumerated(EnumType.STRING)
    private FaixaEtaria faixaEtaria;

    @Enumerated(EnumType.STRING)
    private Objetivo objetivo;

    public Exercicio(DadosCadastroExercicio dados) {
        this.nome = dados.nome();
        this.descricao = dados.descricao();
        this.instrucao = dados.instrucao();
        this.faixaEtaria = dados.faixaEtaria();
        this.objetivo = dados.objetivo();
    }
}