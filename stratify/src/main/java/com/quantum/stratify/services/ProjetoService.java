package com.quantum.stratify.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.quantum.stratify.entities.Projeto;
import com.quantum.stratify.repositories.ProjetoRepository;
import com.quantum.stratify.web.dtos.ProjetoDTO;

@Service
public class ProjetoService {
    
    @Autowired
    private ProjetoRepository projetoRepository;

    public Projeto salvar(Projeto projeto) {
        if (projeto.getNome() == null || projeto.getNome().trim().isEmpty()) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST, "Nome do projeto é obrigatório!");
        }

        // Verifica se já existe um projeto com o mesmo nome
        Optional<Projeto> projetoExistente = projetoRepository.findByNome(projeto.getNome());
        if (projetoExistente.isPresent() && !projetoExistente.get().getId().equals(projeto.getId())) {
            throw new ResponseStatusException(
                HttpStatus.CONFLICT, "Já existe um projeto com este nome!");
        }

        return projetoRepository.save(projeto);
    }

    public Projeto getById(Long id) {
        return projetoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Projeto não encontrado!"));
    }

    public List<ProjetoDTO> getAll() {
        return projetoRepository.findAll().stream()
                .map(projeto -> new ProjetoDTO(projeto.getId(), projeto.getNome()))
                .collect(Collectors.toList());
    }

    public List<ProjetoDTO> buscarProjetosPorUsuarios(Long idUsuario) {
        return projetoRepository.findProjetoByUsuarioId(idUsuario);
    }

    public Optional<Projeto> buscarPorNome(String nome) {
        return projetoRepository.findByNome(nome);
    }
}