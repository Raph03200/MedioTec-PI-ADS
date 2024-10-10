package com.agendasenac.modells;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.agendasenac.repository.UserSistemaRepository;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserSistemaRepository userRepository; // Ou o repositório que você usa para buscar usuários

    @Override
    public UserDetails loadUserByUsername(String imailUser) throws UsernameNotFoundException {
        // Supondo que você tenha uma entidade 'User' com e-mail e senha
        UserSistema user = userRepository.findByimailUser(imailUser).orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado com o email: " + imailUser));

        return new org.springframework.security.core.userdetails.User(
                user.getImailUser(), 
                user.getSenhaAcessoUser(), 
                new ArrayList<>() // Adicione as permissões (Roles) aqui, se necessário
        );
    }
}

