package com.quantum.stratify.services;

import com.quantum.stratify.repositories.UserStoryRepository;
import com.quantum.stratify.web.dtos.TotalCardsDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Service
public class UserStoryService {

    @Autowired
    private UserStoryRepository userStoryRepository;

    @Operation(summary = "Obter o total de cards", description = "Retorna o número total de cards existentes, podendo filtrar por projeto e/ou usuário.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Total de cards retornado com sucesso")
    })
    @PostMapping("/total-cards")
    public TotalCardsDTO getTotalCardCount(
            @RequestParam(required = false) Long idProjeto,
            @RequestParam(required = false) Long idUsuario) {
        
        Long total;
        
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
}
