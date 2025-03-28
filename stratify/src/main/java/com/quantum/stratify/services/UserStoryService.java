package com.quantum.stratify.services;

import com.quantum.stratify.repositories.UserStoryRepository;
import com.quantum.stratify.web.dtos.ResponseQuantidadeCardsByPeriodo;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
public class UserStoryService {
    
    @Autowired
    private UserStoryRepository userStoryRepository;
    
    @Operation(
        summary = "Count user stories by period",
        description = "Returns the count of created and finished user stories within the specified date range",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Successful operation",
                content = @Content(schema = @Schema(implementation = ResponseQuantidadeCardsByPeriodo.class))),
            @ApiResponse(
                responseCode = "400",
                description = "Invalid date range provided"
            )})
    public ResponseQuantidadeCardsByPeriodo countUserStoriesByPeriod(LocalDateTime dataInicio, LocalDateTime dataFim) {
        Long criadas = userStoryRepository.countCriadasNoPeriodo(dataInicio, dataFim);
        Long finalizadas = userStoryRepository.countFinalizadasNoPeriodo(dataInicio, dataFim);
        
        return new ResponseQuantidadeCardsByPeriodo(criadas, finalizadas, null, null);
    }
}