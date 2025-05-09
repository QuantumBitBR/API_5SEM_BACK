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
import com.quantum.stratify.services.UserStoryStatusService;
import com.quantum.stratify.services.UserStoryTagService;
import com.quantum.stratify.web.dtos.PercentualStatusUsuarioDTO;
import com.quantum.stratify.web.dtos.QuantidadeCardsPorSprintDTO;
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

    
    @Autowired
    private UserStoryStatusService userStoryStatusService;


    @Operation(summary = "Obter o total de cards", description = "Retorna o número total de cards existentes, podendo filtrar por projeto e/ou usuário.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Total de cards retornado com sucesso")
    })
    @GetMapping("/total-cards")
    public ResponseEntity<TotalCardsDTO> getTotalCardCount(
            @RequestParam(required = false) Long idProjeto,
            @RequestParam(required = false) Long idUsuario) {
        
        TotalCardsDTO result = userStoryService.getTotalCardCount(idProjeto, idUsuario);
        return ResponseEntity.ok().body(result);
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

    @Operation(summary = "Obter o percentual de user stories por status", description = "Retorna a proporção percentual de user stories agrupadas por status, com filtros opcionais por projeto e usuário")
    @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Percentual de User Stories por status retornado com sucesso")
    })
    @GetMapping("/percentual-por-status")
    public ResponseEntity<List<PercentualStatusUsuarioDTO>> getPercentualPorStatus(
        @RequestParam(required = false) Long projetoId,
        @RequestParam(required = false) Long usuarioId) {
    List<PercentualStatusUsuarioDTO> resultado = userStoryStatusService.getPercentualUserStoriesPorStatus(projetoId, usuarioId);
    return ResponseEntity.ok(resultado);
        }
    
    @Operation(summary = "Obter a quantidade de user stories por sprint", description = "Retorna a quantidade de user stories agrupadas por sprint, com filtros por projeto e usuário")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Quantidade de User Stories por sprint retornada com sucesso")
    })
    @GetMapping("/quantidade-por-sprint")
    public ResponseEntity<?> getQuantidadePorSprint(
        @RequestParam(required = false) Long projetoId, 
        @RequestParam(required = false) Long usuarioId) {
    List<QuantidadeCardsPorSprintDTO> resultado = userStoryService.getQuantidadeUserStoriesBySprint(projetoId, usuarioId);
    return ResponseEntity.ok(resultado);
}

}
