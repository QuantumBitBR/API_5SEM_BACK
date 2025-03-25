package com.quantum.stratify.web.controllers;

import com.quantum.stratify.services.FatoEficienciaUserStoryService;
import com.quantum.stratify.web.dtos.FatoEficienciaTempoMedioDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/fato-eficiencia")
public class FatoEficienciaUserStoryController {
    @Autowired
    private FatoEficienciaUserStoryService fatoEficienciaUserStoryService;

    @GetMapping
    public ResponseEntity<FatoEficienciaTempoMedioDTO> getFatoEficienciaTempoMedio() {
        return ResponseEntity.ok().body(fatoEficienciaUserStoryService.getTempoMedioPorUserStory());
    }
}
