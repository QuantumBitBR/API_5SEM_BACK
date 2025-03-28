package com.quantum.stratify.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.quantum.stratify.repositories.FatoStatusUserStoryRepository;
import com.quantum.stratify.web.dtos.PercentualStatusDTO;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class FatoStatusUserStoryService {

    private final FatoStatusUserStoryRepository fatoStatusUserStoryRepository;
    private final ProjetoService projetoService;

    public List<PercentualStatusDTO> getStatusUserStory(Long projetoId) {
        List<Object[]> resultados;

        if (projetoId == null) {
            resultados = fatoStatusUserStoryRepository.findStatusPercentuaisAll();
        } else {
            resultados = fatoStatusUserStoryRepository.findStatusPercentuais(projetoId);
        }

        return resultados.stream()
            .map(obj -> new PercentualStatusDTO((String) obj[0], (Double) obj[1]))
            .collect(Collectors.toList());
    }
}
