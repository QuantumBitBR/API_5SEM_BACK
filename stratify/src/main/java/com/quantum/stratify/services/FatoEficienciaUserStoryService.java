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
            throw new EntityNotFoundException("Nenhum dado encontrado para o usuário especificado.");
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
        if (usuarioId != null && projetoId != null) {
            List<TempoMedioPorProjetoDTO> results = fatoEficienciaRepository.findByProjetoIdAndUsuarioId(projetoId, usuarioId);
            if (results == null || results.isEmpty()) {
                throw new EntityNotFoundException("Nenhum dado encontrado para o projeto e usuário especificados.");
            }
            return results;
        } else if (usuarioId != null) {
            List<TempoMedioPorProjetoDTO> results = fatoEficienciaRepository.findByUsuarioId(usuarioId);
            if (results == null || results.isEmpty()) {
                throw new EntityNotFoundException("Nenhum dado encontrado para o usuário especificado.");
            }
            return results;
        } else if (projetoId != null) {
            return getTempoMedioPorProjeto(projetoId);
        } else {
            return getTempoMedioTotal();
        }
    }

    public double getMediaTempoFiltrado(Long projetoId, Long usuarioId) {
        List<TempoMedioPorProjetoDTO> results;

        if (usuarioId != null && projetoId != null) {
            results = fatoEficienciaRepository.findByProjetoIdAndUsuarioId(projetoId, usuarioId);
        } else if (usuarioId != null) {
            results = fatoEficienciaRepository.findByUsuarioId(usuarioId);
        } else if (projetoId != null) {
            results = fatoEficienciaRepository.findByProjetoId(projetoId);
        } else {
            results = fatoEficienciaRepository.getAll();
        }

        if (results == null || results.isEmpty()) {
            throw new EntityNotFoundException("Nenhum dado encontrado para os filtros especificados.");
        }

        return results.stream()
                .mapToDouble(TempoMedioPorProjetoDTO::tempoMedio)
                .average()
                .orElse(0.0);
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
            throw new EntityNotFoundException("Nenhum dado encontrado para o usuário especificado.");
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