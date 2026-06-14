package com.ecoafono.service;

import com.ecoafono.dto.DadosEnderecoViaCep;
import com.ecoafono.exception.CepInvalidoException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ViaCepServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private ViaCepService viaCepService;

    @Test
    void deveBuscarEnderecoPorCepValido() {
        String cep = "06010000";

        var endereco = new DadosEnderecoViaCep(
                "Rua Teste",
                "Osasco",
                "SP",
                false
        );

        when(restTemplate.getForObject(
                "https://viacep.com.br/ws/{cep}/json/",
                DadosEnderecoViaCep.class,
                cep
        )).thenReturn(endereco);

        DadosEnderecoViaCep resultado = viaCepService.buscarEndereco(cep);

        assertNotNull(resultado);
        assertEquals("Rua Teste", resultado.logradouro());
        assertEquals("Osasco", resultado.cidade());
        assertEquals("SP", resultado.estado());

        verify(restTemplate, times(1)).getForObject(
                "https://viacep.com.br/ws/{cep}/json/",
                DadosEnderecoViaCep.class,
                cep
        );
    }

    @Test
    void deveRemoverCaracteresDoCepAntesDaBusca() {
        String cepComMascara = "06010-000";
        String cepLimpo = "06010000";

        var endereco = new DadosEnderecoViaCep(
                "Rua Teste",
                "Osasco",
                "SP",
                false
        );

        when(restTemplate.getForObject(
                "https://viacep.com.br/ws/{cep}/json/",
                DadosEnderecoViaCep.class,
                cepLimpo
        )).thenReturn(endereco);

        DadosEnderecoViaCep resultado = viaCepService.buscarEndereco(cepComMascara);

        assertNotNull(resultado);
        assertEquals("Osasco", resultado.cidade());

        verify(restTemplate, times(1)).getForObject(
                "https://viacep.com.br/ws/{cep}/json/",
                DadosEnderecoViaCep.class,
                cepLimpo
        );
    }

    @Test
    void deveLancarExcecaoQuandoCepForInvalido() {
        String cep = "00000000";

        var enderecoInvalido = new DadosEnderecoViaCep(
                null,
                null,
                null,
                true
        );

        when(restTemplate.getForObject(
                "https://viacep.com.br/ws/{cep}/json/",
                DadosEnderecoViaCep.class,
                cep
        )).thenReturn(enderecoInvalido);

        assertThrows(CepInvalidoException.class, () -> viaCepService.buscarEndereco(cep));

        verify(restTemplate, times(1)).getForObject(
                "https://viacep.com.br/ws/{cep}/json/",
                DadosEnderecoViaCep.class,
                cep
        );
    }
}