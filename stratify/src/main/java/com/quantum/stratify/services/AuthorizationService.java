package com.quantum.stratify.services;

import com.quantum.stratify.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationService implements UserDetailsService {

    @Autowired
    UsuarioRepository Repository;

    public UserDetails loadUserByEmail(String email, String password) throws UsernameNotFoundException {
        return Repository.loadUserByEmail(email, password);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return Repository.loadUserByNome(username);
    }

}
