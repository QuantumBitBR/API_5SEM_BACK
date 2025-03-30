package com.quantum.stratify.web.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.quantum.stratify.web.dtos.ResponseQuantidadeCardsByPeriodo;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import com.quantum.stratify.services.FatoUserStoryTemporaisService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/temporais")
@RequiredArgsConstructor
@CrossOrigin("*")
public class FatoUserStoryTemporaisController {

    private final FatoUserStoryTemporaisService fatoUserStoryTemporaisService;

    @Operation(summary = "Get user stories by project", description = "Retrieve metrics of created and finished user stories filtered by project.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Metrics successfully retrieved"),
        @ApiResponse(responseCode = "400", description = "Invalid filter parameters"),
        @ApiResponse(responseCode = "500", description = "Internal server error")})
    @GetMapping
    public List<ResponseQuantidadeCardsByPeriodo> getUserStoriesByPeriodo(
            @RequestParam(required = false) Long projetoId) {
        return fatoUserStoryTemporaisService.getUserStoriesByPeriodo(projetoId);
    }
}