package com.quantum.stratify.web.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.quantum.stratify.services.FatoStatusUserStoryService;
import com.quantum.stratify.web.dtos.PercentualStatusDTO;

@RestController
@CrossOrigin("*")
@RequestMapping("/fatoStatusUserStory")
public class FatoStatusUserStoryController {
    
    @Autowired
    public FatoStatusUserStoryService fatoStatusUserStoryService;
    
    @GetMapping("/quantidade-por-status")
    public ResponseEntity<List<PercentualStatusDTO>> getStatusUserStoryPercentual(
        @RequestParam(required=false) Long projetoId){
            return ResponseEntity.ok(fatoStatusUserStoryService.getStatusUserStory(projetoId));
        }
}
