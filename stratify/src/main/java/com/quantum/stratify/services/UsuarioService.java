package com.quantum.stratify.services;


import java.util.List;

import com.quantum.stratify.web.dtos.UsuarioInfoDTO;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.quantum.stratify.entities.Usuario;
import com.quantum.stratify.enums.Role;
import com.quantum.stratify.repositories.UsuarioRepository;
import com.quantum.stratify.web.dtos.AtribuirGestor;
import com.quantum.stratify.web.dtos.UsuarioDTO;
import com.quantum.stratify.web.exceptions.EntityNotFoundException;
import com.quantum.stratify.web.exceptions.PasswordInvalidException;
import com.quantum.stratify.web.exceptions.UsernameUniqueViolationException;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    private final PasswordEncoder passwordEncoder;


    @Transactional
    public Usuario salvar(Usuario usuario) {
        try {
            usuario.setSenha(passwordEncoder.encode(usuario.getPassword()));
            return usuarioRepository.save(usuario);
        } catch (org.springframework.dao.DataIntegrityViolationException ex) {
            throw new UsernameUniqueViolationException(String.format("Email '%s' já cadastrado", usuario.getUsername()));
        }
    }

    @Transactional
    public Usuario resetarSenha(Long idUsuario, String novaSenha) {
        Usuario usuario = buscarPorId(idUsuario);

        String senhaCriptografada = passwordEncoder.encode(novaSenha);
        usuario.setSenha(senhaCriptografada);
        usuario.setRequireReset(false);

        return usuarioRepository.save(usuario);
    }


    public Usuario getById(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new jakarta.persistence.EntityNotFoundException("Usuario não encontrado com o id: " + id));
    }

    @Transactional(readOnly = true)
    public Usuario buscarPorId(Long id) {
        return usuarioRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format("Usuário id=%s não encontrado", id))
        );
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

    @Transactional
    public Usuario editarSenha(Long id, String senhaAtual, String novaSenha, String confirmaSenha) {
        if (!novaSenha.equals(confirmaSenha)) {
            throw new PasswordInvalidException("Nova senha não confere com confirmação de senha.");
        }

        Usuario user = buscarPorId(id);
        if (!passwordEncoder.matches(senhaAtual, user.getPassword())) {
            throw new PasswordInvalidException("Sua senha não confere.");
        }

        user.setSenha(passwordEncoder.encode(novaSenha));
        return user;
    }
    @Transactional(readOnly = true)
    public List<Usuario> buscarTodos() {
        return usuarioRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Usuario buscarPorEmail(String email) {
        return usuarioRepository.findByEmail(email).orElseThrow(
                () -> new EntityNotFoundException(String.format("Usuario com '%s' não encontrado", email))
        );
    }
    @Transactional(readOnly = true)
    public Role buscarRolePorEmail(String email) {
        return usuarioRepository.findRoleByEmail(email);
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

    
    @Transactional
    public Usuario alterarRole(Long idUsuario, Role novaRole) {
    Usuario usuario = usuarioRepository.findById(idUsuario)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));
    if (novaRole == null) {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Role inválida.");
    }
    usuario.setRole(novaRole);
    return usuarioRepository.save(usuario);
    }

    @Transactional(readOnly = true)
    public List<UsuarioInfoDTO> listarUsuariosInfo() {
        return usuarioRepository.findAll().stream()
                .map(u -> new UsuarioInfoDTO(
                        u.getId(),
                        u.getEmail(),
                        u.getGestor() != null ? u.getGestor().getNome() : null,  // aqui
                        u.getRole(),
                        u.isRequireReset(),
                        u.getIsEnable()
                ))
                .toList();
    }


}



