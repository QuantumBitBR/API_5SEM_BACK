package com.quantum.stratify.services;

import com.quantum.stratify.repositories.UserStoryRepository;
import com.quantum.stratify.web.dtos.QuantidadeCardsPorSprintDTO;
import com.quantum.stratify.web.dtos.TotalCardsDTO;
import com.quantum.stratify.web.exceptions.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserStoryServiceTest {

    @Mock
    private UserStoryRepository userStoryRepository;

    @InjectMocks
    private UserStoryService userStoryService;

    // Testes para getTotalCardCount
    @Test
    void getTotalCardCount_ComProjetoEUsuario_DeveRetornarContagemFiltrada() {
        when(userStoryRepository.countByProjectAndUser(1L, 2L)).thenReturn(5L);
        TotalCardsDTO result = userStoryService.getTotalCardCount(1L, 2L);
        assertEquals(5L, result.quantidadeUserStories());
    }

    @Test
    void getTotalCardCount_ApenasComProjeto_DeveRetornarContagemFiltrada() {
        when(userStoryRepository.countByProject(1L)).thenReturn(10L);
        TotalCardsDTO result = userStoryService.getTotalCardCount(1L, null);
        assertEquals(10L, result.quantidadeUserStories());
    }

    @Test
    void getTotalCardCount_ApenasComUsuario_DeveRetornarContagemFiltrada() {
        when(userStoryRepository.countByUser(2L)).thenReturn(7L);
        TotalCardsDTO result = userStoryService.getTotalCardCount(null, 2L);
        assertEquals(7L, result.quantidadeUserStories());
    }

    @Test
    void getTotalCardCount_SemFiltros_DeveRetornarContagemTotal() {
        when(userStoryRepository.countTotalUserStories()).thenReturn(20L);
        TotalCardsDTO result = userStoryService.getTotalCardCount(null, null);
        assertEquals(20L, result.quantidadeUserStories());
    }

    // Testes para getQuantidadeUserStoriesBySprint
    @Test
    void getQuantidadeUserStoriesBySprint_ComProjetoEUsuario_DeveRetornarAgrupado() {
        Object[] resultado1 = new Object[]{"Sprint 1", 5L};
        Object[] resultado2 = new Object[]{"Sprint 2", 3L};
        
        when(userStoryRepository.countBySprintAndProjectAndUser(1L, 2L))
            .thenReturn(Arrays.asList(resultado1, resultado2));

        List<QuantidadeCardsPorSprintDTO> result = userStoryService.getQuantidadeUserStoriesBySprint(1L, 2L);

        assertEquals(2, result.size());
        assertEquals("Sprint 1", result.get(0).getSprint());
        assertEquals(5L, result.get(0).getQuantidade());
        assertEquals("Sprint 2", result.get(1).getSprint());
        assertEquals(3L, result.get(1).getQuantidade());
    }

    @Test
    void getQuantidadeUserStoriesBySprint_ApenasComProjeto_DeveRetornarAgrupado() {
        Object[] resultado1 = new Object[]{"Sprint 1", 8L};
        Object[] resultado2 = new Object[]{"Sprint 2", 4L};
        
        when(userStoryRepository.countBySprintAndProject(1L))
            .thenReturn(Arrays.asList(resultado1, resultado2));

        List<QuantidadeCardsPorSprintDTO> result = userStoryService.getQuantidadeUserStoriesBySprint(1L, null);

        assertEquals(2, result.size());
        assertEquals("Sprint 1", result.get(0).getSprint());
        assertEquals(8L, result.get(0).getQuantidade());
        assertEquals("Sprint 2", result.get(1).getSprint());
        assertEquals(4L, result.get(1).getQuantidade());
    }

    @Test
    void getQuantidadeUserStoriesBySprint_ApenasComUsuario_DeveRetornarAgrupado() {
        Object[] resultado1 = new Object[]{"Sprint 1", 3L};
        Object[] resultado2 = new Object[]{"Sprint 2", 2L};
        
        when(userStoryRepository.countBySprintAndUser(2L))
            .thenReturn(Arrays.asList(resultado1, resultado2));

        List<QuantidadeCardsPorSprintDTO> result = userStoryService.getQuantidadeUserStoriesBySprint(null, 2L);

        assertEquals(2, result.size());
        assertEquals("Sprint 1", result.get(0).getSprint());
        assertEquals(3L, result.get(0).getQuantidade());
        assertEquals("Sprint 2", result.get(1).getSprint());
        assertEquals(2L, result.get(1).getQuantidade());
    }

    @Test
    void getQuantidadeUserStoriesBySprint_SemFiltros_DeveRetornarTodos() {
        Object[] resultado1 = new Object[]{"Sprint 1", 10L};
        Object[] resultado2 = new Object[]{"Sprint 2", 5L};
        
        when(userStoryRepository.countBySprintAll())
            .thenReturn(Arrays.asList(resultado1, resultado2));

        List<QuantidadeCardsPorSprintDTO> result = userStoryService.getQuantidadeUserStoriesBySprint(null, null);

        assertEquals(2, result.size());
        assertEquals("Sprint 1", result.get(0).getSprint());
        assertEquals(10L, result.get(0).getQuantidade());
        assertEquals("Sprint 2", result.get(1).getSprint());
        assertEquals(5L, result.get(1).getQuantidade());
    }

    @Test
    void getQuantidadeUserStoriesBySprint_ComListaVazia_DeveRetornarListaVazia() {
        when(userStoryRepository.countBySprintAndProjectAndUser(1L, 2L))
            .thenReturn(Collections.emptyList());

        List<QuantidadeCardsPorSprintDTO> result = userStoryService.getQuantidadeUserStoriesBySprint(1L, 2L);
        
        assertTrue(result.isEmpty());
    }

    @Test
    void getQuantidadeUserStoriesBySprint_ComListaVaziaApenasProjeto_DeveRetornarListaVazia() {
        when(userStoryRepository.countBySprintAndProject(1L))
            .thenReturn(Collections.emptyList());

        List<QuantidadeCardsPorSprintDTO> result = userStoryService.getQuantidadeUserStoriesBySprint(1L, null);
        
        assertTrue(result.isEmpty());
    }

    @Test
    void getQuantidadeUserStoriesBySprint_ComListaVaziaApenasUsuario_DeveRetornarListaVazia() {
        when(userStoryRepository.countBySprintAndUser(2L))
            .thenReturn(Collections.emptyList());

        List<QuantidadeCardsPorSprintDTO> result = userStoryService.getQuantidadeUserStoriesBySprint(null, 2L);
        
        assertTrue(result.isEmpty());
    }

    @Test
    void getQuantidadeUserStoriesBySprint_ComListaVaziaSemFiltros_DeveRetornarListaVazia() {
        when(userStoryRepository.countBySprintAll())
            .thenReturn(Collections.emptyList());

        List<QuantidadeCardsPorSprintDTO> result = userStoryService.getQuantidadeUserStoriesBySprint(null, null);
        
        assertTrue(result.isEmpty());
    }
}