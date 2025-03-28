package com.quantum.stratify.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.quantum.stratify.entities.FatoEficienciaUserStory;
import com.quantum.stratify.repositories.FatoEficienciaUserStoryRepository;
import com.quantum.stratify.web.dtos.FatoEficienciaTempoMedioGeralDTO;
import com.quantum.stratify.web.dtos.TempoMedioPorProjetoDTO;
import com.quantum.stratify.web.exceptions.EntityNotFoundException;

@Service
public class FatoEficienciaUserStoryService {
    @Autowired
    private FatoEficienciaUserStoryRepository fatoEficienciaRepository;

    public FatoEficienciaTempoMedioGeralDTO getTempoMedioPorUserStory(Long idUserStory) {
        FatoEficienciaUserStory fatoEficiencia = fatoEficienciaRepository.findByUserStoryId(idUserStory);
        if(fatoEficiencia == null) {
            throw new EntityNotFoundException("UserStory not found");
        }
        return new FatoEficienciaTempoMedioGeralDTO(fatoEficiencia.getTempoMedio());

    }

    public List<TempoMedioPorProjetoDTO> getTempoMedioPorProjeto(Long idProjeto) {
        List<TempoMedioPorProjetoDTO> results = fatoEficienciaRepository.findByProjetoId(idProjeto);
        if(results == null)
            throw new EntityNotFoundException("UserStory not found");

        return fatoEficienciaRepository.findByProjetoId(idProjeto);
    }
}
