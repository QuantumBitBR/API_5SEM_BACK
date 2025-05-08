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

    // Testes existentes para getTotalCardCount (mantidos iguais)
    // ... [os mesmos testes anteriores para getTotalCardCount] ...

    @Test
    void getQuantidadeUserStoriesBySprint_ComProjetoEUsuario_DeveRetornarAgrupadoPorSprint() {
        // Arrange
        Object[] resultado1 = new Object[]{"Sprint 1", 5L};
        Object[] resultado2 = new Object[]{"Sprint 2", 3L};
        
        when(userStoryRepository.countBySprintGroupedAndUser(1L, 2L))
            .thenReturn(Arrays.asList(resultado1, resultado2));

        // Act
        List<QuantidadeCardsPorSprintDTO> result = userStoryService.getQuantidadeUserStoriesBySprint(1L, 2L);

        // Assert
        assertEquals(2, result.size());
        assertEquals("Sprint 1", result.get(0).getSprint());
        assertEquals(5L, result.get(0).getQuantidade());
        assertEquals("Sprint 2", result.get(1).getSprint());
        assertEquals(3L, result.get(1).getQuantidade());
    }

    @Test
    void getQuantidadeUserStoriesBySprint_ApenasComProjeto_DeveRetornarAgrupadoPorSprint() {
        // Arrange
        Object[] resultado1 = new Object[]{"Sprint 1", 8L};
        Object[] resultado2 = new Object[]{"Sprint 2", 4L};
        
        when(userStoryRepository.countBySprintGrouped(1L))
            .thenReturn(Arrays.asList(resultado1, resultado2));

        // Act
        List<QuantidadeCardsPorSprintDTO> result = userStoryService.getQuantidadeUserStoriesBySprint(1L, null);

        // Assert
        assertEquals(2, result.size());
        assertEquals("Sprint 1", result.get(0).getSprint());
        assertEquals(8L, result.get(0).getQuantidade());
        assertEquals("Sprint 2", result.get(1).getSprint());
        assertEquals(4L, result.get(1).getQuantidade());
    }

    @Test
    void getQuantidadeUserStoriesBySprint_ComListaVaziaEUsuario_DeveLancarExcecao() {
        // Arrange
        when(userStoryRepository.countBySprintGroupedAndUser(1L, 2L))
            .thenReturn(Collections.emptyList());

        // Act & Assert
        EntityNotFoundException exception = assertThrows(
            EntityNotFoundException.class,
            () -> userStoryService.getQuantidadeUserStoriesBySprint(1L, 2L)
        );
        
        assertEquals("Nenhuma user story encontrada para os critérios especificados", exception.getMessage());
    }

    @Test
    void getQuantidadeUserStoriesBySprint_ComListaVaziaSemUsuario_DeveLancarExcecao() {
        // Arrange
        when(userStoryRepository.countBySprintGrouped(1L))
            .thenReturn(Collections.emptyList());

        // Act & Assert
        EntityNotFoundException exception = assertThrows(
            EntityNotFoundException.class,
            () -> userStoryService.getQuantidadeUserStoriesBySprint(1L, null)
        );
        
        assertEquals("Nenhuma user story encontrada para os critérios especificados", exception.getMessage());
    }
}