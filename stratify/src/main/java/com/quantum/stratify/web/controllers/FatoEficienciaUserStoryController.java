package com.quantum.stratify.web.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping("/projeto-tempo-medio-us")
    @Operation(summary = "Tempo médio por projeto (filtrável por usuário)", description = "Retorna tempo médio, descrição e ID de todas as userStories de um projeto. Pode ser filtrado por ID de usuário.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca efetuada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Projeto não foi encontrado")
    })
    public ResponseEntity<List<TempoMedioPorProjetoDTO>> getTempoMedioPorProjeto(
            @RequestParam(required = false, name = "projetoId") Long projetoId,
            @RequestParam(required = false, name = "usuarioId") Long usuarioId) {
        return ResponseEntity.ok().body(
                fatoEficienciaUserStoryService.getTempoMedioPorProjeto(projetoId, usuarioId));
    }

    @Operation(summary = "Tempo médio por projeto com filtro opcional por usuário e/ou projeto.", description = "Retorna lista de tempos médios por user story, podendo filtrar, de forma opcional, por um projeto ou por usuário")
    @GetMapping("/projeto")
    public ResponseEntity<List<TempoMedioPorProjetoDTO>> getTempoMedioPorProjetoFiltrado(
            @RequestParam(required = false) Long projetoId,
            @RequestParam(required = false) Long usuarioId) {

        return ResponseEntity.ok().body(fatoEficienciaUserStoryService.getTempoMedioFiltrado(projetoId, usuarioId));
    }

    @Operation(summary = "Média de tempo por projeto com filtro opcional por usuário e/ou projeto.", description = "Retorna o valor numérico da média do tempo por user story, podendo filtrar, de forma opcional, por um projeto ou por usuário")
    @GetMapping("/media-tempo")
    public ResponseEntity<FatoEficienciaTempoMedioGeralDTO> getMediaTempoComFiltro(
            @RequestParam(required = false) Long projetoId,
            @RequestParam(required = false) Long usuarioId) {
        double media = fatoEficienciaUserStoryService.getMediaTempoFiltrado(projetoId, usuarioId);
        return ResponseEntity.ok(new FatoEficienciaTempoMedioGeralDTO(media));
    }

}
