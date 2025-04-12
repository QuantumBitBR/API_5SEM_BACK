package com.quantum.stratify.services;

import com.quantum.stratify.entities.Usuario;
import com.quantum.stratify.repositories.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Usuario getById(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuario não encontrado com o id: " + id));
    }
    
    
    public void ativarUsuario(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
            .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));
        usuario.setIsEnable(true);
        usuarioRepository.save(usuario);
    }

    public void desativarUsuario(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
            .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));
        usuario.setIsEnable(false);
        usuarioRepository.save(usuario);
    }

}
