package com.quantum.stratify.services;

import com.quantum.stratify.repositories.UserStoryRepository;
import com.quantum.stratify.web.dtos.QuantidadeCardsPorSprintDTO;
import com.quantum.stratify.web.dtos.TotalCardsDTO;
import com.quantum.stratify.web.exceptions.EntityNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

@Service
public class UserStoryService {

    @Autowired
    private UserStoryRepository userStoryRepository;

    
    public TotalCardsDTO getTotalCardCount(
            @RequestParam(required = false) Long idProjeto,
            @RequestParam(required = false) Long idUsuario) {
        
        Long total = 0L;
        
        if (idProjeto != null && idUsuario != null) {
            // Filtro por projeto e usuário
            total = userStoryRepository.countByProjectAndUser(idProjeto, idUsuario);
        } else if (idProjeto != null) {
            // Filtro apenas por projeto
            total = userStoryRepository.countByProject(idProjeto);
        } else if (idUsuario != null) {
            // Filtro apenas por usuário
            total = userStoryRepository.countByUser(idUsuario);
        } else {
            // Sem filtros - conta todos
            total = userStoryRepository.countTotalUserStories();
        }
        
        return new TotalCardsDTO(total);
    }

    public List<QuantidadeCardsPorSprintDTO> getQuantidadeUserStoriesBySprint(Long projetoId, Long usuarioId) {
        // Não precisa verificar projetoId null pois o controller já validou
        List<Object[]> resultados = new ArrayList<>();
        
        if (projetoId != null && usuarioId != null) {
            resultados = userStoryRepository.countBySprintAndProjectAndUser(projetoId, usuarioId);
        } 
        else if (projetoId != null) {
            resultados = userStoryRepository.countBySprintAndProject(projetoId);
        }
        else if (usuarioId != null) {
            resultados = userStoryRepository.countBySprintAndUser(usuarioId);
        }
        else {
            resultados = userStoryRepository.countBySprintAll();
        }
        
        return resultados.stream()
                .map(result -> new QuantidadeCardsPorSprintDTO(
                    (String) result[0], 
                    (Long) result[1]))
                .collect(Collectors.toList());
    }
}
