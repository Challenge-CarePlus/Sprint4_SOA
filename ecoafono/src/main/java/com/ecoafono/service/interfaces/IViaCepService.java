package com.ecoafono.service.interfaces;

import com.ecoafono.dto.DadosEnderecoViaCep;

public interface IViaCepService {

    DadosEnderecoViaCep buscarEndereco(String cep);
}