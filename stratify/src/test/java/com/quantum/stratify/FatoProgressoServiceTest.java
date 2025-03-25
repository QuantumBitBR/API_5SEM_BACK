package com.quantum.stratify;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.quantum.stratify.repositories.FatoProgressoRepository;
import com.quantum.stratify.services.FatoProgressoService;

@ExtendWith(MockitoExtension.class)
public class FatoProgressoServiceTest {

    @Mock
    private FatoProgressoRepository fatoProgressoRepository;

    @InjectMocks
    private FatoProgressoService fatoProgressoService;

    @Test
    public void getTotalCardCount_DeveRetornarTotalCards() {
        long expectedTotal = 5L;
        when(fatoProgressoRepository.countTotalCards()).thenReturn(expectedTotal);

        long actualTotal = fatoProgressoService.getTotalCardCount();

        assertEquals(expectedTotal, actualTotal);
    }

    @Test
    public void getTotalCardCount_QuandoSemCards_DeveRetornarZero() {
        long expectedTotal = 0L;
        when(fatoProgressoRepository.countTotalCards()).thenReturn(expectedTotal);

        long actualTotal = fatoProgressoService.getTotalCardCount();

        assertEquals(expectedTotal, actualTotal);
    }
}