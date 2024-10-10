package com.agendasenac.controllers;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.agendasenac.modells.LoginRequest;
import com.agendasenac.modells.UserSistema;
import com.agendasenac.repository.UserSistemaRepository;
import com.agendasenac.services.AuthenticationService;
import com.agendasenac.services.RegraUsers;

@RestController
public class LoginController {

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private UserSistemaRepository usersistema;

    @PostMapping("/login")
    @CrossOrigin
    public ResponseEntity<Map<String, String>> login(@RequestBody LoginRequest authRequest) {
        try {
            // Autentica o usuário e gera o token
            String token = authenticationService.authenticate(authRequest.getUserEmail(), authRequest.getUserSenha());

            Optional<UserSistema> optionalUsuario = usersistema.findByimailUser(authRequest.getUserEmail());

            Map<String, String> response = new HashMap<>();
            response.put("Token", token);

            if (optionalUsuario.isPresent()) {
                UserSistema usuario = optionalUsuario.get(); // Obtém o usuário do Optional
                // Cria uma string com os dados do usuário que você deseja retornar
                String Nome = usuario.getNomeCompletoUser();
                RegraUsers Tipo = usuario.getTipoUser();
                response.put("NomeUsuario", Nome); 
                response.put("TipoUser", "" + Tipo );
            } else {
                response.put("DadosUser", "Usuário não encontrado"); // Mensagem se não encontrar
            }

            return ResponseEntity.ok(response);
        } catch (AuthenticationException e) {
            // Se houver erro de autenticação, retorne 401 Unauthorized
            Map<String, String> response = new HashMap<>();
            response.put("Acesso", "Negado");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }
}
