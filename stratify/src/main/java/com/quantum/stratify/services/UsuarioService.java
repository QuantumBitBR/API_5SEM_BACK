package com.quantum.stratify.services;

import com.quantum.stratify.entities.Usuario;
import com.quantum.stratify.repositories.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.quantum.stratify.web.dtos.AtribuirGestor;
import com.quantum.stratify.web.dtos.UsuarioDTO;
import com.quantum.stratify.web.dtos.UsuarioPorRoleDTO;
import com.quantum.stratify.enums.Role;

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

    public void atribuirLideradosAoGestor(AtribuirGestor dto) {
        Usuario gestor = usuarioRepository.findById(dto.getIdUsuarioGestor())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Gestor não encontrado"));
    
        for (Long idLiderado : dto.getListaIdLiderados()) {
            Usuario liderado = usuarioRepository.findById(idLiderado)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário liderado não encontrado: " + idLiderado));
            
            liderado.setGestor(gestor);
            usuarioRepository.save(liderado);
        }
    }
    public List<UsuarioDTO> buscarUsuariosPorProjetoEGestor(Long idProjeto, Long idGestor){
        return usuarioRepository.findUsuarioByProjetoAndGestor(idProjeto, idGestor);
    }
    
     public List<UsuarioPorRoleDTO> listarPorRole(Role role) {
          List<UsuarioPorRoleDTO> usuarios = usuarioRepository.findByRole(role);
    
            if (usuarios.isEmpty()) {
             throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Nenhum usuário encontrado com a role: " + role );
      }
    
          return usuarios;
}

}
