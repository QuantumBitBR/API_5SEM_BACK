package com.quantum.stratify.services;

import java.util.List;
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

    public Projeto getById(Long id){
        return projetoRepository.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"Projeto não encontrado!"));
    }

    public List<ProjetoDTO> getAll(){
        return projetoRepository.findAll().stream().map(projeto->{
            return new ProjetoDTO(projeto.getId(), projeto.getNome());
        }).collect(Collectors.toList());
    }

    public List<ProjetoDTO> buscarProjetosPorUsuarios(Long idUsuario){
        return projetoRepository.findProjetoByUsuarioId(idUsuario);
    }

}


