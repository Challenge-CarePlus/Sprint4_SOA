package com.ecoafono.controller;

import com.ecoafono.config.security.filter.SecurityFilter;
import com.ecoafono.dto.DadosDetalhamentoExercicio;
import com.ecoafono.enums.FaixaEtaria;
import com.ecoafono.enums.Objetivo;
import com.ecoafono.service.interfaces.IExercicioService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ExercicioController.class)
@AutoConfigureMockMvc(addFilters = false)
class ExercicioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private IExercicioService exercicioService;

    @MockitoBean
    private SecurityFilter securityFilter;

    @Test
    @DisplayName("Deve listar exercícios quando usuário estiver autenticado")
    @WithMockUser(roles = "ADMIN")
    void deveListarExercicios() throws Exception {
        var exercicio = new DadosDetalhamentoExercicio(
                1L,
                "Sopro controlado",
                "Exercício para controle do ar.",
                "Sopre lentamente por 5 segundos.",
                FaixaEtaria.CRIANCA,
                Objetivo.FALA
        );

        when(exercicioService.buscarExercicios(null, null))
                .thenReturn(List.of(exercicio));

        mockMvc.perform(get("/exercicios"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].nome").value("Sopro controlado"))
                .andExpect(jsonPath("$[0].faixaEtaria").value("CRIANCA"))
                .andExpect(jsonPath("$[0].objetivo").value("FALA"));
    }
}