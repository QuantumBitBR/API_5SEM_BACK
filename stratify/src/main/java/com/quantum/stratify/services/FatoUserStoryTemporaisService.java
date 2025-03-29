package com.quantum.stratify.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.quantum.stratify.entities.FatoUserStoryTemporais;
import com.quantum.stratify.repositories.FatoUserStoryTemporaisRepository;
import com.quantum.stratify.repositories.PeriodoRepository;

import com.quantum.stratify.web.dtos.ResponseQuantidadeCardsByPeriodo;

import jakarta.transaction.Transactional;


@Service
public class FatoUserStoryTemporaisService {
    
    @Autowired
    private FatoUserStoryTemporaisRepository fatoUserStoryTemporaisRepository;

    @Autowired
    private PeriodoRepository periodoRepository;

    @Autowired
    private ProjetoService projetoService;

    @Transactional
    public List<ResponseQuantidadeCardsByPeriodo> getUserStoriesByPeriodo(Long projetoId, Long periodoId) {
        List<FatoUserStoryTemporais> resultados = new ArrayList<>();
        
        if (projetoId != null && periodoId != null) {
            // Busca por projeto e período específico
            resultados = fatoUserStoryTemporaisRepository
                .findByProjetoAndPeriodo(
                    projetoService.getById(projetoId), 
                    periodoRepository.findById(periodoId).orElseThrow()
                );
        } else if (periodoId != null) {
            // Busca apenas por período
            resultados = fatoUserStoryTemporaisRepository
                .findByPeriodo(periodoRepository.findById(periodoId).orElseThrow());

        } else {
            // Busca todos os registros
            resultados = fatoUserStoryTemporaisRepository.findAll();
        }

        List<ResponseQuantidadeCardsByPeriodo> dtos = new ArrayList<>();
        for (FatoUserStoryTemporais fato : resultados) {
            dtos.add(new ResponseQuantidadeCardsByPeriodo(
                fato.getPeriodo().getNome(),
                fato.getQuantidadeUserStoriesCriadas(),
                fato.getQuantidadeUserStoriesFinalizadas()
            ));
        }
        
        return dtos;
    }
}