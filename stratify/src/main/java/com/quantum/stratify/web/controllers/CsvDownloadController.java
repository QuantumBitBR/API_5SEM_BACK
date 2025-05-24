package com.quantum.stratify.web.controllers;

import java.io.IOException;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.quantum.stratify.services.CsvDownloadService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@RestController
@RequestMapping("/download")
@CrossOrigin("*")
@Slf4j
@Tag(name = "CSV Download", description = "Exportação de relatórios em formato CSV")
public class CsvDownloadController {

    private final CsvDownloadService csvDownloadService;

    @Operation(summary = "Exporta dados filtrados em formato CSV")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Arquivo CSV gerado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Parâmetros inválidos"),
        @ApiResponse(responseCode = "404", description = "Dados não encontrados"),
        @ApiResponse(responseCode = "500", description = "Erro interno ao gerar o CSV")
    })
    @GetMapping("/csv")
    public ResponseEntity<InputStreamResource> downloadCsv(
            @Parameter(description = "Tipo de exportação (ex: cardsporsprint, cardsporperiodo, etc.)", required = true)
            @RequestParam String exportType,

            @Parameter(description = "ID do projeto (opcional)")
            @RequestParam(required = false) Long projectId,

            @Parameter(description = "ID do usuário (opcional)")
            @RequestParam(required = false) Long userId
    ) throws IOException {

        String filename = exportType + ".csv";
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename);
        headers.add(HttpHeaders.CONTENT_TYPE, "text/csv");

        InputStreamResource resource = csvDownloadService.generateCsv(projectId, userId, exportType);
        return new ResponseEntity<>(resource, headers, HttpStatus.OK);
    }
}
