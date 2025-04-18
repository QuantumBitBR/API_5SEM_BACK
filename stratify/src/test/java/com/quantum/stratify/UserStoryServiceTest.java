package com.quantum.stratify;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.quantum.stratify.repositories.UserStoryRepository;
import com.quantum.stratify.services.UserStoryService;
import com.quantum.stratify.web.dtos.TotalCardsDTO;

@ExtendWith(MockitoExtension.class)
public class UserStoryServiceTest {

    @Mock
    private UserStoryRepository userStoryRepository;

    @InjectMocks
    private UserStoryService userStoryService;

    @Test
    void whenGetTotalUserStoryCount_thenReturnCorrectNumber() {
        // Dado
        when(userStoryRepository.countTotalUserStories()).thenReturn(5L);

        // Quando
        TotalCardsDTO result = userStoryService.getTotalCardCount(null, null);

        System.out.println("Resultado do teste (sem filtros): " + result);
        
        // Então
        assertEquals(5L, result.quantidadeUserStories());
    }

    @Test
    void whenNoUserStories_thenReturnZero() {
        // Dado
        when(userStoryRepository.countTotalUserStories()).thenReturn(0L);

        // Quando
        TotalCardsDTO result = userStoryService.getTotalCardCount(null, null);

        System.out.println("Resultado do teste (sem user stories): " + result);
        
        // Então
        assertEquals(0L, result.quantidadeUserStories());
    }

    @Test
    void whenFilterByProject_thenReturnProjectCount() {
        // Dado
        Long projectId = 1L;
        when(userStoryRepository.countByProject(projectId)).thenReturn(3L);

        // Quando
        TotalCardsDTO result = userStoryService.getTotalCardCount(projectId, null);

        System.out.println("Resultado do teste (filtro por projeto): " + result);
        
        // Então
        assertEquals(3L, result.quantidadeUserStories());
    }

    @Test
    void whenFilterByUser_thenReturnUserCount() {
        // Dado
        Long userId = 2L;
        when(userStoryRepository.countByUser(userId)).thenReturn(4L);

        // Quando
        TotalCardsDTO result = userStoryService.getTotalCardCount(null, userId);

        System.out.println("Resultado do teste (filtro por usuário): " + result);
        
        // Então
        assertEquals(4L, result.quantidadeUserStories());
    }

    @Test
    void whenFilterByProjectAndUser_thenReturnCombinedCount() {
        // Dado
        Long projectId = 1L;
        Long userId = 2L;
        when(userStoryRepository.countByProjectAndUser(projectId, userId)).thenReturn(2L);

        // Quando
        TotalCardsDTO result = userStoryService.getTotalCardCount(projectId, userId);

        System.out.println("Resultado do teste (filtro por projeto e usuário): " + result);
        
        // Então
        assertEquals(2L, result.quantidadeUserStories());
    }

    @Test
    void whenFilterByNonExistentProject_thenReturnZero() {
        // Dado
        Long nonExistentProjectId = 999L;
        when(userStoryRepository.countByProject(nonExistentProjectId)).thenReturn(0L);

        // Quando
        TotalCardsDTO result = userStoryService.getTotalCardCount(nonExistentProjectId, null);

        System.out.println("Resultado do teste (projeto inexistente): " + result);
        
        // Então
        assertEquals(0L, result.quantidadeUserStories());
    }
}