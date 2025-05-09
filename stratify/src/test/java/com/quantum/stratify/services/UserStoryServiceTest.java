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

    // Testes para getTotalCardCount (mantidos)
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
        
        when(userStoryRepository.countBySprintGroupedAndUser(1L, 2L))
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
        
        when(userStoryRepository.countBySprintGrouped(1L))
            .thenReturn(Arrays.asList(resultado1, resultado2));

        List<QuantidadeCardsPorSprintDTO> result = userStoryService.getQuantidadeUserStoriesBySprint(1L, null);

        assertEquals(2, result.size());
        assertEquals("Sprint 1", result.get(0).getSprint());
        assertEquals(8L, result.get(0).getQuantidade());
        assertEquals("Sprint 2", result.get(1).getSprint());
        assertEquals(4L, result.get(1).getQuantidade());
    }

    @Test
    void getQuantidadeUserStoriesBySprint_SemFiltros_DeveRetornarTodos() {
        Object[] resultado1 = new Object[]{"Sprint 1", 10L};
        Object[] resultado2 = new Object[]{"Sprint 2", 5L};
        
        when(userStoryRepository.countBySprintGroupedAll())
            .thenReturn(Arrays.asList(resultado1, resultado2));

        List<QuantidadeCardsPorSprintDTO> result = userStoryService.getQuantidadeUserStoriesBySprint(null, null);

        assertEquals(2, result.size());
        assertEquals("Sprint 1", result.get(0).getSprint());
        assertEquals(10L, result.get(0).getQuantidade());
        assertEquals("Sprint 2", result.get(1).getSprint());
        assertEquals(5L, result.get(1).getQuantidade());
    }

    @Test
    void getQuantidadeUserStoriesBySprint_ComListaVaziaEUsuario_DeveLancarExcecao() {
        when(userStoryRepository.countBySprintGroupedAndUser(1L, 2L))
            .thenReturn(Collections.emptyList());

        EntityNotFoundException exception = assertThrows(
            EntityNotFoundException.class,
            () -> userStoryService.getQuantidadeUserStoriesBySprint(1L, 2L)
        );
        
        assertEquals("Nenhuma user story encontrada para os critérios especificados", exception.getMessage());
    }

    @Test
    void getQuantidadeUserStoriesBySprint_ComListaVaziaSemUsuario_DeveLancarExcecao() {
        when(userStoryRepository.countBySprintGrouped(1L))
            .thenReturn(Collections.emptyList());

        EntityNotFoundException exception = assertThrows(
            EntityNotFoundException.class,
            () -> userStoryService.getQuantidadeUserStoriesBySprint(1L, null)
        );
        
        assertEquals("Nenhuma user story encontrada para os critérios especificados", exception.getMessage());
    }

}