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

    public List<TempoMedioPorProjetoDTO> getTempoMedioPorProjeto(Long idProjeto) {
        List<TempoMedioPorProjetoDTO> results = fatoEficienciaRepository.findByProjetoId(idProjeto);
        if (results == null || results.isEmpty()) {
            throw new EntityNotFoundException("UserStory not found");
        }

        return results;
    }

    public FatoEficienciaTempoMedioGeralDTO getTempoMedioTotalPorProjeto(Long projetoId) {
        List<TempoMedioPorProjetoDTO> userStories = getTempoMedioPorProjeto(projetoId);
        Double tempoMedio = 0.0;

        for (TempoMedioPorProjetoDTO userStory : userStories) {
            tempoMedio += userStory.tempoMedio();
        }
        tempoMedio = userStories.isEmpty() ? 0.0 : tempoMedio / userStories.size();
        return new FatoEficienciaTempoMedioGeralDTO(tempoMedio / userStories.size());

    }
    public List<TempoMedioPorProjetoDTO> getTempoMedioFiltrado(Long projetoId, Long usuarioId) {
        if (usuarioId != null) {
            List<TempoMedioPorProjetoDTO> results = fatoEficienciaRepository.findByProjetoIdAndUsuarioId(projetoId, usuarioId);
            if (results == null || results.isEmpty()) {
                throw new EntityNotFoundException("Nenhum dado encontrado para o projeto e usuário especificados.");
            }
            return results;
        } else {
            return getTempoMedioPorProjeto(projetoId);
        }
    }


    public List<TempoMedioPorProjetoDTO> getTempoMedioTotal() {

        return fatoEficienciaRepository.getAll();

    }
}