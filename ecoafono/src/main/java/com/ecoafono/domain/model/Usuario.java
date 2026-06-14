package com.ecoafono.domain.model;

import com.ecoafono.dto.DadosAtualizacaoPreferencias;
import com.ecoafono.dto.DadosCadastroUsuario;
import com.ecoafono.enums.FaixaEtaria;
import com.ecoafono.enums.Objetivo;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "usuarios")
@Entity(name = "Usuario")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String email;
    private String cep;
    private String logradouro;
    private String cidade;
    private String estado;

    @Enumerated(EnumType.STRING)
    private FaixaEtaria faixaEtaria;

    @Enumerated(EnumType.STRING)
    private Objetivo objetivo;

    public Usuario(DadosCadastroUsuario dados, String logradouro, String cidade, String estado) {
        this.nome = dados.nome();
        this.email = dados.email();
        this.cep = dados.cep();
        this.logradouro = logradouro;
        this.cidade = cidade;
        this.estado = estado;
        this.faixaEtaria = dados.faixaEtaria();
        this.objetivo = dados.objetivo();
    }

    public void atualizarPreferencias(DadosAtualizacaoPreferencias dados) {
        this.faixaEtaria = dados.faixaEtaria();
        this.objetivo = dados.objetivo();
    }
}