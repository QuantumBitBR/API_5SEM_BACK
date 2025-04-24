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
        if (fatoEficiencia == null) {
            throw new EntityNotFoundException("UserStory not found");
        }
        return new FatoEficienciaTempoMedioGeralDTO(fatoEficiencia.getTempoMedio());

    }

    public List<TempoMedioPorProjetoDTO> getTempoMedioTotal() {

        return fatoEficienciaRepository.getAll();

    }

    public List<TempoMedioPorProjetoDTO> getTempoMedioPorProjeto(Long idProjeto, Long usuarioId) {
        List<TempoMedioPorProjetoDTO> results;
    
        if (usuarioId != null) {
            results = fatoEficienciaRepository.findByProjetoIdAndUsuarioId(idProjeto, usuarioId);
        } else {
            results = fatoEficienciaRepository.findByProjetoId(idProjeto);
        }
    
        if (results == null || results.isEmpty()) {
            throw new EntityNotFoundException("UserStory not found");
        }
    
        return results;
    }
    
    public FatoEficienciaTempoMedioGeralDTO getTempoMedioTotalPorProjeto(Long projetoId, Long usuarioId) {
        List<TempoMedioPorProjetoDTO> userStories = getTempoMedioPorProjeto(projetoId, usuarioId);
        Double tempoMedio = 0.0;
    
        for (TempoMedioPorProjetoDTO userStory : userStories) {
            tempoMedio += userStory.tempoMedio();
        }
    
        tempoMedio = userStories.isEmpty() ? 0.0 : tempoMedio / userStories.size();
        return new FatoEficienciaTempoMedioGeralDTO(tempoMedio);
    }
}