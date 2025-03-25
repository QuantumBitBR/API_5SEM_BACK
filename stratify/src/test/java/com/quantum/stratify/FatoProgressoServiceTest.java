package com.quantum.stratify;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

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
    private FatoProgressoRepository fatoProgressoRepository; // Mock do repositório

    @InjectMocks
    private FatoProgressoService fatoProgressoService; // Injeta o mock no service

    @Test
    void quandoChamarGetTotalCardCount_deveRetornarSomaTotalDasUserStories() {
        when(fatoProgressoRepository.countTotalCards()).thenReturn(15L);

        Long resultado = fatoProgressoService.getTotalCardCount();

        assertEquals(15L, resultado); // Valida o retorno
        verify(fatoProgressoRepository, times(1)).countTotalCards();
    }

    @Test
    void quandoNaoHouverRegistros_deveRetornarZero() {
        when(fatoProgressoRepository.countTotalCards()).thenReturn(0L);

        assertEquals(0L, fatoProgressoService.getTotalCardCount());
        verify(fatoProgressoRepository, times(1)).countTotalCards();
    }
}