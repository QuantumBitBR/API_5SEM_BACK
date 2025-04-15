package com.quantum.stratify.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.quantum.stratify.entities.Usuario;
import com.quantum.stratify.repositories.UsuarioRepository;
import com.quantum.stratify.web.dtos.UsuarioDTO;

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

    public List<UsuarioDTO> buscarUsuariosPorProjetoEGestor(Long idProjeto, Long idGestor){
        return usuarioRepository.findUsuarioByProjetoAndGestor(idProjeto, idGestor);
    }

}
