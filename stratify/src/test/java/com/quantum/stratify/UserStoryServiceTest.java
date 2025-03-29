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
        TotalCardsDTO result = userStoryService.getTotalCardCount();

        System.out.println("Resultado do teste: " + result);
        System.out.println("Quantidade de User Stories: " + result.quantidadeUserStories());

        // Então
        assertEquals(5L, result.quantidadeUserStories());
    }

    @Test
    void whenNoUserStories_thenReturnZero() {
        // Dado
        when(userStoryRepository.countTotalUserStories()).thenReturn(0L);

        // Quando
        TotalCardsDTO result = userStoryService.getTotalCardCount();

        System.out.println("Resultado do teste: " + result);
        System.out.println("Quantidade de User Stories: " + result.quantidadeUserStories());
        
        // Então
        assertEquals(0L, result.quantidadeUserStories());
    }
}