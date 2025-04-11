package com.quantum.stratify.web.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.quantum.stratify.web.dtos.ResponseQuantidadeCardsByPeriodo;

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

    @Operation(summary = "Get user stories by project", description = "Retrieve metrics of created and finished user stories filtered by project.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Metrics successfully retrieved"),
        @ApiResponse(responseCode = "400", description = "Invalid filter parameters"),
        @ApiResponse(responseCode = "500", description = "Internal server error")})
    @GetMapping("/projeto/{idProjeto}/usuario")
    public List<ResponseQuantidadeCardsByPeriodo> getUserStoriesByPeriodoAndUser(
            @PathVariable Long idProjeto,
            @RequestParam(required = false)
            @Parameter(description = "User ID to filter. If not provided, returns metrics for all users in the project")
            Long idUsuario) {
        return fatoUserStoryTemporaisService.getUserStoriesByPeriodoAndUser(idProjeto, idUsuario);
    }
}