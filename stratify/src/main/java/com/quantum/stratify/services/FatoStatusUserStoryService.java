package com.quantum.stratify.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.quantum.stratify.entities.FatoStatusUserStory;
import com.quantum.stratify.repositories.FatoStatusUserStoryRepository;
import com.quantum.stratify.web.dtos.PercentualStatusDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FatoStatusUserStoryService {

    private final FatoStatusUserStoryRepository fatoStatusUserStoryRepository;
    private final ProjetoService projetoService;

    @Transactional
    public List<PercentualStatusDTO> getStatusUserStory(Long projetoId) {
        List<FatoStatusUserStory> resultados = new ArrayList<>();
        if (projetoId == null) {
            resultados = fatoStatusUserStoryRepository.findAll();
        } else {
            resultados = fatoStatusUserStoryRepository.findByProjeto(projetoService.getById(projetoId));
        }

        Map<String, Integer> statusMap = new HashMap<>();
        int totalCards = 0;
        for (FatoStatusUserStory fato : resultados) {
            String status = String.valueOf(fato.getStatus().getType());
            statusMap.put(status, statusMap.getOrDefault(status, 0) + fato.getQuantidadeUserStory());
            totalCards += fato.getQuantidadeUserStory();
        }
        List<PercentualStatusDTO> percentuais = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : statusMap.entrySet()) {
            percentuais.add(new PercentualStatusDTO(entry.getKey(), calcularPercentual(totalCards, entry.getValue())));
        }
        return percentuais;
    }

    public Double calcularPercentual(Integer totalCards, Integer quantCardStatus) {
        if(totalCards == 0 || quantCardStatus == 0) return 0.0;
        return ((double) (quantCardStatus * 100) / totalCards);
    }
}
