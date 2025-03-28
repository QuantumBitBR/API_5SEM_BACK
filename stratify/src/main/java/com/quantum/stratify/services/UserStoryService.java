package com.quantum.stratify.services;

import com.quantum.stratify.repositories.UserStoryRepository;
import com.quantum.stratify.web.dtos.ResponseQuantidadeCardsByPeriodo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
public class UserStoryService {
    
    @Autowired
    private UserStoryRepository userStoryRepository;
    
    public ResponseQuantidadeCardsByPeriodo countUserStoriesByPeriod(LocalDateTime dataInicio, LocalDateTime dataFim) {
        Long criadas = userStoryRepository.countCriadasNoPeriodo(dataInicio, dataFim);
        Long finalizadas = userStoryRepository.countFinalizadasNoPeriodo(dataInicio, dataFim);
        
        return new ResponseQuantidadeCardsByPeriodo(criadas, finalizadas, null, null);
    }
}