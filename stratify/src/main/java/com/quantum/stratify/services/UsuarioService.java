package com.quantum.stratify.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.quantum.stratify.entities.Usuario;
import com.quantum.stratify.repositories.UsuarioRepository;
import com.quantum.stratify.web.dtos.AtribuirGestor;

@Service
public class UsuarioService {
    
    @Autowired
    private UsuarioRepository usuarioRepository;

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

    public void atribuirLideradosAoGestor(Long idUsuario, AtribuirGestor dto) {
        Usuario gestor = usuarioRepository.findById(dto.getIdUsuarioGestor())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Gestor não encontrado"));
    
        for (Long idLiderado : dto.getListaIdLiderados()) {
            Usuario liderado = usuarioRepository.findById(idLiderado)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário liderado não encontrado: " + idLiderado));
            
            liderado.setGestor(gestor);
            usuarioRepository.save(liderado);
        }
    }
}
