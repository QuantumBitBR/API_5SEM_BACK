package com.quantum.stratify.web.controllers;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.*;

import com.quantum.stratify.services.FatoProgressoService;
import com.quantum.stratify.web.dtos.ResponseQuantidadeCardsByPeriodo;
import com.quantum.stratify.web.dtos.ResponseQuantidadeCardsByTags;

@RestController
@CrossOrigin("*")
@RequestMapping("/fatoProgresso")
public class FatoProgressoController {

    @Autowired
    private FatoProgressoService fatoProgressoService;

    
    @GetMapping("/quantidade-por-etiqueta")
    public List<ResponseQuantidadeCardsByTags> getQuantidadePorEtiqueta(
            @RequestParam(required = false) Long projetoId) {
        return fatoProgressoService.getQuantidadeUserStoriesByTag(projetoId);
    }

    @GetMapping("/count-by-period")
    @ApiOperation(value = "Get card statistics by period", 
                 notes = "Returns the total number of cards created and finished between the specified dates for a project")
    public ResponseEntity<ResponseQuantidadeCardsByPeriodo> getCardCountByPeriod(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam Long projetoId) {
        
        ResponseQuantidadeCardsByPeriodo response = 
            fatoProgressoService.getCardCountByPeriod(startDate, endDate, projetoId);
        return ResponseEntity.ok(response);
    }
}
