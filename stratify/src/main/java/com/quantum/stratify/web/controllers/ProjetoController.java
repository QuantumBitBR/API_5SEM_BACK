package com.quantum.stratify.web.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.quantum.stratify.services.FatoProgressoService;
import com.quantum.stratify.services.ProjetoService;
import com.quantum.stratify.web.dtos.ProjetoDTO;
import com.quantum.stratify.web.dtos.ResponseQuantidadeCardsByTags;

@RestController
@CrossOrigin("*")
@RequestMapping("/projetos")
public class ProjetoController {

    @Autowired
    private ProjetoService projetoService;

    @GetMapping("/all")
    public List<ProjetoDTO> getAllProjetos() {
        return projetoService.getAll();
    }
}
