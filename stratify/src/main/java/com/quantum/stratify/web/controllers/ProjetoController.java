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
    @Operation(summary = "Busca todos os projetos", description = "Retorna uma lista de todos os projetos cadastrados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de projetos retornada com sucesso"),
            // @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public List<ProjetoDTO> getAllProjetos() {
        return projetoService.getAll();
    }

    @GetMapping("/porUsuario")
    @Operation(summary = "Busca de projeto por idUsuario", description = "Retorna uma lista de projetos filtrados por ID de usuário ")
    @ApiResponses( value = {
        @ApiResponse(responseCode = "200", description = "Lista de resumos de projetos retornada com sucesso")
    })
    public List<ProjetoDTO> getProjetosUsuario(
        @RequestParam(name = "idUsuario", required = false) Long idUsuario) {
            return projetoService.buscarProjetosPorUsuarios(idUsuario);
        }
}
