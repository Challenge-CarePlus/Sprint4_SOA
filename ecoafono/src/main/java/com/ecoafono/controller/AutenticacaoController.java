package com.ecoafono.controller;

import com.ecoafono.config.security.dto.DadosTokenJWT;
import com.ecoafono.config.security.service.TokenService;
import com.ecoafono.domain.model.UsuarioSistema;
import com.ecoafono.dto.DadosAutenticacao;
import com.ecoafono.enums.Role;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
public class AutenticacaoController {

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity<DadosTokenJWT> efetuarLogin(@RequestBody @Valid DadosAutenticacao dados) {
        UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken(dados.login(), dados.senha());

        Authentication authentication = manager.authenticate(token);

        UsuarioSistema usuario = (UsuarioSistema) authentication.getPrincipal();

        if (usuario.getPerfil() != Role.ADMIN) {
            return ResponseEntity.status(403).build();
        }

        String tokenJWT = tokenService.gerarToken(usuario);

        return ResponseEntity.ok(new DadosTokenJWT(tokenJWT));
    }
}