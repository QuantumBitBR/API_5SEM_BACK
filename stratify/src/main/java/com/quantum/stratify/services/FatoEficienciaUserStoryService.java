package com.quantum.stratify.services;

import com.quantum.stratify.entities.FatoEficienciaUserStory;
import com.quantum.stratify.repositories.FatoEficienciaUserStoryRepository;
import com.quantum.stratify.web.dtos.FatoEficienciaTempoMedioDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class FatoEficienciaUserStoryService {
    @Autowired
    private FatoEficienciaUserStoryRepository fatoEficienciaRepository;

    public FatoEficienciaTempoMedioDTO getTempoMedioPorUserStory(){

        List<FatoEficienciaUserStory> listaFatosEficiencia = fatoEficienciaRepository.findAll();
        Double tempoMedioPorUserStory = 0.0;

        for(FatoEficienciaUserStory fato :  listaFatosEficiencia){
            tempoMedioPorUserStory += fato.getTempoMedio();
        }

        return new FatoEficienciaTempoMedioDTO(tempoMedioPorUserStory/listaFatosEficiencia.size());
    }
}
