package com.quantum.stratify.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.quantum.stratify.services.CardService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@CrossOrigin("*")
@RequestMapping("/cards")
@Tag(name = "Cards", description = "Endpoints para gerenciamento de cards")
public class CardController {

    @Autowired
    private CardService cardService;

    @GetMapping("/total")
    @Operation(
        summary = "Retorna a quantidade total de cards",
        description = "Este endpoint retorna o número total de cards cadastrados no sistema.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Quantidade total de cards retornada com sucesso"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
        }
    )
    public long getTotalCards() {
        return cardService.getTotalCards();
    }
}
