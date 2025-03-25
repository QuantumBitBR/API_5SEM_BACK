package com.quantum.stratify.web.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.quantum.stratify.services.FatoProgressoService;
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
}
