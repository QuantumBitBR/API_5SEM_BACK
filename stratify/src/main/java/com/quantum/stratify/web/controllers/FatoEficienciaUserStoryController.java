package com.quantum.stratify.web.controllers;

import com.quantum.stratify.services.FatoEficienciaUserStoryService;
import com.quantum.stratify.web.dtos.FatoEficienciaTempoMedioGeralDTO;
import com.quantum.stratify.web.dtos.TempoMedioPorProjetoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/fatoeficiencia")
public class FatoEficienciaUserStoryController {
    @Autowired
    private FatoEficienciaUserStoryService fatoEficienciaUserStoryService;

    @GetMapping("/userStory/{userStoryId}")
    public ResponseEntity<FatoEficienciaTempoMedioGeralDTO> getFatoEficienciaTempoMedioPorUserStory(@PathVariable Long userStoryId) {
        return ResponseEntity.ok().body(fatoEficienciaUserStoryService.getTempoMedioPorUserStory(userStoryId));
    }

    @GetMapping("/projeto/{projetoId}")
    public ResponseEntity<List<TempoMedioPorProjetoDTO>> getTempoMedioPorProjeto(@PathVariable Long projetoId) {
        return ResponseEntity.ok().body(fatoEficienciaUserStoryService.getTempoMedioPorProjeto(projetoId));
    }
}
