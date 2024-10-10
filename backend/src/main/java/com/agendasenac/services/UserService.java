package com.agendasenac.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.agendasenac.modells.UserSistema;
import com.agendasenac.repository.UserSistemaRepository;

@Service
public class UserService {

    
    @Autowired
	private PasswordEncoder passwordEncoder;

    @Autowired
    private UserSistemaRepository userRepository;
    
    

    public UserSistema criarUser(UserSistema user) {
        // Codificar a senha antes de salvar
        user.setSenhaAcessoUser(passwordEncoder.encode(user.getSenhaAcessoUser()));
        return userRepository.save(user);
    }
}

