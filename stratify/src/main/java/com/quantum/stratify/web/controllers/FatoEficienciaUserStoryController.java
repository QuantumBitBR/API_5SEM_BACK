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
    FatoEficienciaUserStoryService fatoEficienciaUserStoryService;


    @Operation(summary = "Tempo medio por projeto", description = "Retorna tempo médio, descricao e ID de todas as userStories de um projeto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca efetuada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Projeto não foi encontrada")
    })
    @GetMapping("/projeto/{projetoId}")// FILTRAR POR USUARIO TBM
    public ResponseEntity<List<TempoMedioPorProjetoDTO>> getTempoMedioPorProjeto(@PathVariable Long projetoId) {
        return ResponseEntity.ok().body(fatoEficienciaUserStoryService.getTempoMedioPorProjeto(projetoId));
    }

    @Operation(summary = "Tempo medio por projeto", description = "Retorna tempo médio entre todas as userStories de um projeto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca efetuada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Projeto não foi encontrada")
    })
    @GetMapping("/total")// FILTRAR POR USUARIO 
    public ResponseEntity<FatoEficienciaTempoMedioGeralDTO> getTempoMedioPorProjetoTotal(@RequestParam Long projetoId) {
        return ResponseEntity.ok().body(fatoEficienciaUserStoryService.getTempoMedioTotalPorProjeto(projetoId));
    }

    @Operation(summary = "Tempo médio por projeto com filtro opcional por usuário e/ou projeto.",
            description = "Retorna lista de tempos médios por user story, podendo filtrar, de forma opcional, por um projeto ou por usuário")
    @GetMapping("/projeto/tempo-medio")
    public ResponseEntity<List<TempoMedioPorProjetoDTO>> getTempoMedioPorProjetoFiltrado(
            @RequestParam(required = false) Long projetoId,
            @RequestParam(required = false) Long usuarioId) {

        return ResponseEntity.ok().body(fatoEficienciaUserStoryService.getTempoMedioFiltrado(projetoId, usuarioId));
    }

}
