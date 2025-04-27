package com.quantum.stratify.web.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.quantum.stratify.services.ProjetoService;
import com.quantum.stratify.web.dtos.ProjetoDTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@CrossOrigin("*")
@RequestMapping("/projetos")
@Tag(name = "Projects", description = "Endpoints related to projects")
public class ProjetoController {

    @Autowired
    private ProjetoService projetoService;

    @GetMapping("/all")
    @Operation(summary = "Get all projects", description = "Returns a list of all registered projects")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of projects returned successfully"),
            // @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public List<ProjetoDTO> getAllProjetos() {
        return projetoService.getAll();
    }

    @GetMapping("/porUsuario")
    @Operation(summary = "Get project summaries", description = "Returns a list of projects filtered by optional user ID")
    @ApiResponses( value = {
        @ApiResponse(responseCode = "200", description = "List of project summaries returned succesfully")
    })
    public List<ProjetoDTO> getProjetosUsuario(
        @RequestParam(name = "idUsuario", required = false) Long idUsuario) {
            return projetoService.buscarProjetosPorUsuarios(idUsuario);
        }
}
