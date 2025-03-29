package com.quantum.stratify.web.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.quantum.stratify.web.dtos.ResponseQuantidadeCardsByPeriodo;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import com.quantum.stratify.services.FatoUserStoryTemporaisService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/temporais")
@RequiredArgsConstructor
public class FatoUserStoryTemporaisController {

    private final FatoUserStoryTemporaisService fatoUserStoryTemporaisService;

    @Operation(summary = "Get user stories by time period", description = "Retrieve metrics of created and finished user stories filtered by project and/or period")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Metrics successfully retrieved"),
        @ApiResponse(responseCode = "400", description = "Invalid filter parameters"),
        @ApiResponse(responseCode = "500", description = "Internal server error")})
    @GetMapping
    public List<ResponseQuantidadeCardsByPeriodo> getUserStoriesByPeriodo(
            @RequestParam(required = false) Long projetoId,
            @RequestParam(required = false) Long periodoId) {
        return fatoUserStoryTemporaisService.getUserStoriesByPeriodo(projetoId, periodoId);
    }
}