package com.quantum.stratify.web.controllers;

import com.quantum.stratify.services.FatoEficienciaUserStoryService;
import com.quantum.stratify.web.dtos.FatoEficienciaTempoMedioGeralDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/fatoeficiencia")
public class FatoEficienciaUserStoryController {
    @Autowired
    private FatoEficienciaUserStoryService fatoEficienciaUserStoryService;

    @GetMapping
    public ResponseEntity<FatoEficienciaTempoMedioGeralDTO> getFatoEficienciaTempoMedioPorUserStory(@RequestParam Long idUserStory) {
        return ResponseEntity.ok().body(fatoEficienciaUserStoryService.getTempoMedioPorUserStory(idUserStory));
    }
}
