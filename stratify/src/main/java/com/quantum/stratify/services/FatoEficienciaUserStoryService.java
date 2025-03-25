package com.quantum.stratify.services;

import com.quantum.stratify.entities.FatoEficienciaUserStory;
import com.quantum.stratify.repositories.FatoEficienciaUserStoryRepository;
import com.quantum.stratify.web.dtos.FatoEficienciaTempoMedioGeralDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class FatoEficienciaUserStoryService {
    @Autowired
    private FatoEficienciaUserStoryRepository fatoEficienciaRepository;

    public void getTempoMedioPorUserStory(Long idUserStory) {

        //List<FatoEficienciaUserStory> listaFatosEficiencia = fatoEficienciaRepository.findAll();
        System.out.println("Cheguei aqui");
        Double tempoMedioPorUserStory = 0.0;

        /*for(FatoEficienciaUserStory fato :  listaFatosEficiencia){
            tempoMedioPorUserStory += fato.getTempoMedio();
        }
*/
//        return new FatoEficienciaTempoMedioGeralDTO(tempoMedioPorUserStory/listaFatosEficiencia.size());
    }
}
