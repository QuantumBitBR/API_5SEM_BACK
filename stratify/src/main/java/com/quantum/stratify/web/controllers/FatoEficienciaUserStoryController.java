package com.quantum.stratify.web.controllers;

import com.quantum.stratify.services.FatoEficienciaUserStoryService;
import com.quantum.stratify.web.dtos.FatoEficienciaTempoMedioGeralDTO;
import com.quantum.stratify.web.dtos.TempoMedioPorProjetoDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/fatoeficiencia")
@Tag(name = "Eficiencia", description = "Retona tempo medio por cards e projetos")
@CrossOrigin("*")
public class FatoEficienciaUserStoryController {
    @Autowired
    private FatoEficienciaUserStoryService fatoEficienciaUserStoryService;

    @Operation(summary = "Tempo medio por UserStory", description = "Tempo médio de uma única UserStory")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca efetuada com sucesso"),
            @ApiResponse(responseCode = "404", description = "UserStory não foi encontrada")
    })
    @GetMapping("/userStory/{userStoryId}")
    public ResponseEntity<FatoEficienciaTempoMedioGeralDTO> getFatoEficienciaTempoMedioPorUserStory(@PathVariable Long userStoryId) {
        return ResponseEntity.ok().body(fatoEficienciaUserStoryService.getTempoMedioPorUserStory(userStoryId));
    }

    @Operation(summary = "Tempo medio por projeto", description = "Retorna tempo médio, descricao e ID de todas as userStories de um projeto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca efetuada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Projeto não foi encontrada")
    })
    @GetMapping("/projeto/{projetoId}")
    public ResponseEntity<List<TempoMedioPorProjetoDTO>> getTempoMedioPorProjeto(@PathVariable Long projetoId) {
        return ResponseEntity.ok().body(fatoEficienciaUserStoryService.getTempoMedioPorProjeto(projetoId));
    }

    @Operation(summary = "Tempo medio por projeto", description = "Retorna tempo médio entre todas as userStories de um projeto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca efetuada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Projeto não foi encontrada")
    })
    @GetMapping("/total")
    public ResponseEntity<FatoEficienciaTempoMedioGeralDTO> getTempoMedioPorProjetoTotal(@RequestParam Long projetoId) {
        return ResponseEntity.ok().body(fatoEficienciaUserStoryService.getTempoMedioTotalPorProjeto(projetoId));
    }

    @Operation(summary = "Tempo medio geral de todos os projetos", description = "Retorna tempo médio entre todas as userStories de todos projeto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca efetuada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Projeto não foi encontrada")
    })
    @GetMapping("/projeto/todos")
    public ResponseEntity<FatoEficienciaTempoMedioGeralDTO> getTempoMedioTotal() {
        return ResponseEntity.ok().body(fatoEficienciaUserStoryService.getTempoMedioTotal());
    }
}
