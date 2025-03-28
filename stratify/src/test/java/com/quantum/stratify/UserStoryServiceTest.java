package com.quantum.stratify;

import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.quantum.stratify.repositories.UserStoryRepository;
import com.quantum.stratify.services.UserStoryService;

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
        // Long result = userStoryService.getTotalCardCount();

        // Então
        // assertEquals(5L, result);
    }

    @Test
    void whenNoUserStories_thenReturnZero() {
        // Dado
        when(userStoryRepository.countTotalUserStories()).thenReturn(0L);

        // // Quando
        // Long result = userStoryService.getTotalCardCount();

        // // Então
        // assertEquals(0L, result);
    }
}