package com.quantum.stratify.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import com.quantum.stratify.entities.FatoTagUserStory;
import com.quantum.stratify.entities.Projeto;
import com.quantum.stratify.repositories.FatoProgressoRepository;
import com.quantum.stratify.web.dtos.ResponseQuantidadeCardsByTags;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@Service
public class FatoProgressoService {

    @Autowired
    private FatoProgressoRepository fatoProgressoRepository;
    @Autowired
    private ProjetoService projetoService;

    public List<ResponseQuantidadeCardsByTags> getQuantidadeUserStoriesByTag(Long projetoId) {
        List<FatoTagUserStory> resultados;
        if(projetoId == null){
            resultados = getAll();
        }
        else{
            Projeto projeto = projetoService.getById(projetoId);
            resultados = fatoProgressoRepository.findByProjeto(projeto);
        }
        return resultados.stream()
        .map(obj -> {
            return new ResponseQuantidadeCardsByTags(obj.getTag().getId(), obj.getTag().getNome(),
            obj.getProjeto().getId(), obj.getQuantidadeUserStories());
        }
        )
    .collect(Collectors.toList());
    }

    public List<FatoTagUserStory> getAll(){
        return fatoProgressoRepository.findAll();
    }

    
    @GetMapping("/total-cards")
    public Long  getTotalCardCount() {
        return fatoProgressoRepository.countTotalCards();
    }
}
