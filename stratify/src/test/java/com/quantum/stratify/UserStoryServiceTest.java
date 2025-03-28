package com.quantum.stratify;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.quantum.stratify.repositories.UserStoryRepository;
import com.quantum.stratify.services.UserStoryService;
import com.quantum.stratify.web.dtos.ResponseQuantidadeCardsByPeriodo;

@ExtendWith(MockitoExtension.class)
public class UserStoryServiceTest {

    @Mock
    private UserStoryRepository userStoryRepository;

    @InjectMocks
    private UserStoryService userStoryService;

    @Test
    void whenCountUserStoriesByPeriod_thenReturnCorrectCounts() {
        // Dados de teste
        LocalDateTime dataInicio = LocalDateTime.of(2023, 1, 1, 0, 0);
        LocalDateTime dataFim = LocalDateTime.of(2023, 1, 31, 23, 59);
        
        // Configuração dos mocks
        when(userStoryRepository.countCriadasNoPeriodo(dataInicio, dataFim)).thenReturn(10L);
        when(userStoryRepository.countFinalizadasNoPeriodo(dataInicio, dataFim)).thenReturn(5L);
        
        // Chamada do método a ser testado
        ResponseQuantidadeCardsByPeriodo response = userStoryService.countUserStoriesByPeriod(dataInicio, dataFim);
        
        // Verificações
        assertEquals(10L, response.quantidadeCriadas());
        assertEquals(5L, response.quantidadeFinalizadas());
    }

    @Test
    void whenCountUserStoriesByPeriodWithNoResults_thenReturnZero() {
        // Dados de teste
        LocalDateTime dataInicio = LocalDateTime.of(2023, 2, 1, 0, 0);
        LocalDateTime dataFim = LocalDateTime.of(2023, 2, 28, 23, 59);
        
        // Configuração dos mocks para retornar zero (nenhum resultado)
        when(userStoryRepository.countCriadasNoPeriodo(dataInicio, dataFim)).thenReturn(0L);
        when(userStoryRepository.countFinalizadasNoPeriodo(dataInicio, dataFim)).thenReturn(0L);
        
        // Chamada do método a ser testado
        ResponseQuantidadeCardsByPeriodo response = userStoryService.countUserStoriesByPeriod(dataInicio, dataFim);
        
        // Verificações
        assertEquals(0L, response.quantidadeCriadas());
        assertEquals(0L, response.quantidadeFinalizadas());
    }

    @Test
    void whenCountUserStoriesByPeriodWithNullValues_thenReturnZero() {
        // Dados de teste
        LocalDateTime dataInicio = LocalDateTime.of(2023, 3, 1, 0, 0);
        LocalDateTime dataFim = LocalDateTime.of(2023, 3, 31, 23, 59);
        
        // Configuração dos mocks para retornar null (simulando possível comportamento do repositório)
        when(userStoryRepository.countCriadasNoPeriodo(dataInicio, dataFim)).thenReturn(null);
        when(userStoryRepository.countFinalizadasNoPeriodo(dataInicio, dataFim)).thenReturn(null);
        
        // Chamada do método a ser testado
        ResponseQuantidadeCardsByPeriodo response = userStoryService.countUserStoriesByPeriod(dataInicio, dataFim);
        
        // Verificações - o DTO deve converter null para 0
        assertEquals(0L, response.quantidadeCriadas());
        assertEquals(0L, response.quantidadeFinalizadas());
    }
}