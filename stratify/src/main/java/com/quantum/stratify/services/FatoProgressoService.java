package com.quantum.stratify.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.quantum.stratify.entities.FatoProgressoUserStory;
import com.quantum.stratify.entities.Projeto;
import com.quantum.stratify.repositories.FatoProgressoRepository;
import com.quantum.stratify.web.dtos.ResponseQuantidadeCardsByTags;


@Service
public class FatoProgressoService {

    @Autowired
    private FatoProgressoRepository fatoProgressoRepository;
    @Autowired
    private ProjetoService projetoService;

    public List<ResponseQuantidadeCardsByTags> getQuantidadeUserStoriesByTag(Long projetoId) {
        List<FatoProgressoUserStory> resultados;
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

    public List<FatoProgressoUserStory> getAll(){
        return fatoProgressoRepository.findAll();
    }
}
