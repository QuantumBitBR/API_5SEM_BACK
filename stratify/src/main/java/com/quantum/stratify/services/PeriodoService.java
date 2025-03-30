package com.quantum.stratify.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.quantum.stratify.entities.Periodo;
import com.quantum.stratify.repositories.PeriodoRepository;
import com.quantum.stratify.web.dtos.PeriodoDTO;

@Service
public class PeriodoService {
    @Autowired
    private PeriodoRepository periodoRepository;

    public Periodo getById(Long id) {
        return periodoRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Período não encontrado!"));
    }

    public List<PeriodoDTO> getAll() {
        return periodoRepository.findAll().stream()
            .map(periodo -> new PeriodoDTO(periodo.getId(), periodo.getNome()))
            .collect(Collectors.toList());
    }

    public List<PeriodoDTO> findByNomeContaining(String termo) {
        return periodoRepository.findByNomeContainingIgnoreCase(termo).stream()
            .map(periodo -> new PeriodoDTO(periodo.getId(), periodo.getNome()))
            .collect(Collectors.toList());
    }
}
