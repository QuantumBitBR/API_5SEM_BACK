package com.quantum.stratify.web.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.quantum.stratify.services.FatoEficienciaUserStoryService;
import com.quantum.stratify.web.dtos.FatoEficienciaTempoMedioGeralDTO;
import com.quantum.stratify.web.dtos.TempoMedioPorProjetoDTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/fatoeficiencia")
@Tag(name = "Eficiencia", description = "Retona tempo medio por cards e projetos")
@CrossOrigin("*")
public class FatoEficienciaUserStoryController {
    @Autowired
    private FatoEficienciaUserStoryService fatoEficienciaUserStoryService;

    @GetMapping("/projeto/{projetoId}")
    @Operation(
        summary = "Tempo médio por projeto (filtrável por usuário)",
        description = "Retorna tempo médio, descrição e ID de todas as userStories de um projeto. Pode ser filtrado por ID de usuário."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Busca efetuada com sucesso"),
        @ApiResponse(responseCode = "404", description = "Projeto não foi encontrado")
    })
    public ResponseEntity<List<TempoMedioPorProjetoDTO>> getTempoMedioPorProjeto(
        @PathVariable Long projetoId,
        @RequestParam(required = false, name = "usuarioId") Long usuarioId) {
    return ResponseEntity.ok().body(
        fatoEficienciaUserStoryService.getTempoMedioPorProjeto(projetoId, usuarioId)
    );
    }

    @GetMapping("/total")
    @Operation(
        summary = "Tempo médio geral por projeto (filtrável por usuário)",
        description = "Retorna o tempo médio de todas as userStories de um projeto. Pode ser filtrado por ID de usuário."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Busca efetuada com sucesso"),
        @ApiResponse(responseCode = "404", description = "Projeto não foi encontrado")
    })
    public ResponseEntity<FatoEficienciaTempoMedioGeralDTO> getTempoMedioPorProjetoTotal(
        @RequestParam Long projetoId,
        @RequestParam(required = false, name = "usuarioId") Long usuarioId) {

    return ResponseEntity.ok().body(
        fatoEficienciaUserStoryService.getTempoMedioTotalPorProjeto(projetoId, usuarioId)
    );
    }
}
