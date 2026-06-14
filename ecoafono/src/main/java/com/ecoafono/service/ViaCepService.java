package com.ecoafono.service;

import com.ecoafono.dto.DadosEnderecoViaCep;
import com.ecoafono.exception.CepInvalidoException;
import com.ecoafono.service.interfaces.IViaCepService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ViaCepService implements IViaCepService {

    @Autowired
    private RestTemplate restTemplate;

    private static final String VIA_CEP_URL = "https://viacep.com.br/ws/{cep}/json/";

    @Override
    public DadosEnderecoViaCep buscarEndereco(String cep) {
        String cepLimpo = cep.replaceAll("[^0-9]", "");

        try {
            DadosEnderecoViaCep endereco = restTemplate.getForObject(
                    VIA_CEP_URL,
                    DadosEnderecoViaCep.class,
                    cepLimpo
            );

            if (endereco == null || endereco.isCepInvalido()) {
                throw new CepInvalidoException(cep);
            }

            return endereco;
        } catch (CepInvalidoException exception) {
            throw exception;
        } catch (Exception exception) {
            throw new CepInvalidoException(cep);
        }
    }
}