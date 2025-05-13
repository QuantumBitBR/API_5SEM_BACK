package com.quantum.stratify.web.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.quantum.stratify.entities.Periodo;
import com.quantum.stratify.services.PeriodoService;
import com.quantum.stratify.web.dtos.PeriodoDTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/periodo")
@RequiredArgsConstructor
@CrossOrigin("*")
public class PeriodoController {

    private final PeriodoService periodoService;

    @GetMapping
    @Operation(summary = "Busca de todos os periodos", description = "Recuperar uma lista de todos os períodos disponíveis")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de períodos recuperada com sucesso"),
        @ApiResponse(responseCode = "500", description = "Erro interno do servidor")})
    public List<PeriodoDTO> getAll() {
        return periodoService.getAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca de periodos por Id", description = "Recuperar um período específico pelo seu Id único")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Período encontrado e retornado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Período não encontrado"),
        @ApiResponse(responseCode = "500", description = "Erro interno do servidor")})
    public Periodo getById(@PathVariable Long id) {
        return periodoService.getById(id);
    }

    @GetMapping("/buscar")
    @Operation(summary = "Busca de periodos por nome", description = "Buscar períodos por nome")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de períodos recuperada com sucesso"),
        @ApiResponse(responseCode = "400", description = "Parâmetro inválido"),
        @ApiResponse(responseCode = "500", description = "Erro interno do servidor")})
    public List<PeriodoDTO> buscarPorNome(@RequestParam String termo) {
        return periodoService.findByNomeContaining(termo);
    }
}