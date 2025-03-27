package com.quantum.stratify.services;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.quantum.stratify.entities.FatoTagUserStory;
import com.quantum.stratify.entities.FatoUserStoryTemporais;
import com.quantum.stratify.entities.Projeto;
import com.quantum.stratify.repositories.FatoProgressoRepository;
import com.quantum.stratify.repositories.FatoUserStoryTemporaisRepository;
import com.quantum.stratify.web.dtos.ResponseQuantidadeCardsByTags;
import com.quantum.stratify.web.dtos.ResponseQuantidadeCardsByPeriodo;


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

    @Autowired
    private  FatoUserStoryTemporaisRepository repository;
    private  DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public ResponseQuantidadeCardsByPeriodo getCardCountByPeriod(LocalDate startDate, LocalDate endDate, Long projetoId) {
        // Converter LocalDate para String no formato esperado no banco (assumindo que o nome do período é yyyy-MM-dd)
        String startDateStr = startDate.format(dateFormatter);
        String endDateStr = endDate.format(dateFormatter);
        
        List<FatoUserStoryTemporais> registros = repository.findByPeriodoAndProjeto(
            startDateStr, endDateStr, projetoId);
        
        int totalCriadas = registros.stream()
                .mapToInt(FatoUserStoryTemporais::getQuantidadeUserStoriesCriadas)
                .sum();
                
        int totalFinalizadas = registros.stream()
                .mapToInt(FatoUserStoryTemporais::getQuantidadeUserStoriesFinalizadas)
                .sum();
                
        return new ResponseQuantidadeCardsByPeriodo();
    }

    public List<FatoTagUserStory> getAll(){
        return fatoProgressoRepository.findAll();
    }
}
