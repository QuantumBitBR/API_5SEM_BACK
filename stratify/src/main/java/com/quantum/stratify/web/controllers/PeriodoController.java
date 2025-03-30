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
    @Operation(summary = "Get all periods", description = "Retrieve a list of all available time periods")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved list of periods"),
        @ApiResponse(responseCode = "500", description = "Internal server error")})
    public List<PeriodoDTO> getAll() {
        return periodoService.getAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get period by ID", description = "Retrieve a specific period by its unique identifier")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Period found and returned"),
        @ApiResponse(responseCode = "404", description = "Period not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")})
    public Periodo getById(@PathVariable Long id) {
        return periodoService.getById(id);
    }

    @GetMapping("/buscar")
    @Operation(summary = "Search periods by name", description = "Search periods containing the specified term in their name")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "List of matching periods returned"),
        @ApiResponse(responseCode = "400", description = "Invalid search parameter"),
        @ApiResponse(responseCode = "500", description = "Internal server error")})
    public List<PeriodoDTO> buscarPorNome(@RequestParam String termo) {
        return periodoService.findByNomeContaining(termo);
    }
}