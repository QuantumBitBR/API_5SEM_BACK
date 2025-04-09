package com.quantum.stratify.web.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.quantum.stratify.services.UserStoryService;
import com.quantum.stratify.services.UserStoryTagService;
import com.quantum.stratify.web.dtos.QuantidadeCardsPorTagDTO;
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

    @Autowired
    private UserStoryTagService userStoryTagService;

    @GetMapping("/total-cards")
    public ResponseEntity<TotalCardsDTO> getTotalCardCount() {
        return ResponseEntity.ok().body(userStoryService.getTotalCardCount());
    }

    @Operation(summary = "Obter a quantidade de user story por tag", description = "Método para buscar quantidade de User Story por tag filtrando por usuario e projeto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Quantidade de Tags por User Story buscada com successo") })
    @GetMapping("/quantidade-por-tag")
    public ResponseEntity<List<QuantidadeCardsPorTagDTO>> getQuantidadePorTag(
            @RequestParam(required = false) Long projetoId, @RequestParam(required = false) Long usuarioId) {
        List<QuantidadeCardsPorTagDTO> resultado = userStoryTagService.getQuantidadeUserStoriesByTag(projetoId,
                usuarioId);
        return ResponseEntity.ok(resultado);
    }
}
