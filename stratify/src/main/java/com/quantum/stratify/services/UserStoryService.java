package com.quantum.stratify.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import com.quantum.stratify.repositories.UserStoryRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@Service
public class UserStoryService {

    @Autowired
    private UserStoryRepository UserStoryRepository;

    @Operation(summary = "Obter o total de cards", description = "Retorna o número total de cards existentes.")
     @ApiResponses(value = {
         @ApiResponse(responseCode = "200", description = "Total de cards retornado com sucesso")
     })
    @GetMapping("/total-cards")
    public Long  getTotalCardCount() {
        return UserStoryRepository.countTotalUserStories();
    }
    
}
