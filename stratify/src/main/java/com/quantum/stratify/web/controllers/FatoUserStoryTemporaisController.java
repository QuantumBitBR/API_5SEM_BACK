package com.quantum.stratify.web.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.quantum.stratify.web.dtos.ResponseQuantidadeCardsByPeriodo;
import com.quantum.stratify.web.exceptions.EntityNotFoundException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import com.quantum.stratify.services.FatoUserStoryTemporaisService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/temporais")
@RequiredArgsConstructor
@CrossOrigin("*")
public class FatoUserStoryTemporaisController {

    @Autowired
    private FatoUserStoryTemporaisService fatoUserStoryTemporaisService;

    @Operation(summary = "Busca de UserStories por projeto", description = "Recuperar métricas de histórias de usuário criadas e finalizadas filtradas por projeto.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Métricas recuperadas com sucesso"),
        @ApiResponse(responseCode = "400", description = "Parâmetro inválido"),
        @ApiResponse(responseCode = "500", description = "Erro interno do servidor")})
    @GetMapping("/projeto")
    public List<ResponseQuantidadeCardsByPeriodo> getUserStoriesByPeriodoAndUser(
            @RequestParam(required = false)
            @Parameter(description = "idProjeto para filtrar")

            Long idProjeto,
            
            @RequestParam(required = false)
            @Parameter(description = "idUsuário para filtrar. Se não fornecido, retorna métricas para todos os usuários do projeto")
            Long idUsuario) {
        return fatoUserStoryTemporaisService.getUserStoriesByPeriodoAndUser(idProjeto, idUsuario);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> handleEntityNotFoundException(EntityNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }
}