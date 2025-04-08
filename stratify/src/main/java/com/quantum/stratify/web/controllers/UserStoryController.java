package com.quantum.stratify.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.quantum.stratify.services.UserStoryService;
import com.quantum.stratify.web.dtos.TotalCardsDTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@CrossOrigin("*")
@RequestMapping("/userStory")
public class UserStoryController {

    @Autowired
    private UserStoryService userStoryService;

    @Operation(summary = "Obter o total de cards", description = "Retorna o número total de cards existentes, podendo filtrar por projeto e/ou usuário.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Total de cards retornado com sucesso")
    })
    @PostMapping("/total-cards")
    public ResponseEntity<TotalCardsDTO> getTotalCardCount(
            @RequestParam(required = false) Long idProjeto,
            @RequestParam(required = false) Long idUsuario) {
        
        TotalCardsDTO result = userStoryService.getTotalCardCount(idProjeto, idUsuario);
        return ResponseEntity.ok().body(result);
    }
}
