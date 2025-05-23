package com.quantum.stratify.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.io.InputStreamResource;

import com.quantum.stratify.web.dtos.QuantidadeCardsPorSprintDTO;
import com.quantum.stratify.web.dtos.ResponseQuantidadeCardsByPeriodo;

class CsvDownloadServiceTest {

    @Mock
    private UserStoryService userStoryService;
    @Mock
    private FatoUserStoryTemporaisService fatoUserStoryTemporaisService;
    @Mock
    private UserStoryTagService userStoryTagService;
    @Mock
    private UserStoryStatusService userStoryStatusService;
    @Mock
    private FatoEficienciaUserStoryService fatoEficienciaUserStoryService;

    @InjectMocks
    private CsvDownloadService csvDownloadService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldGenerateCsvForCardsporsprint() throws Exception {
        when(userStoryService.getQuantidadeUserStoriesBySprint(1L, 1L)).thenReturn(
            List.of(new QuantidadeCardsPorSprintDTO("Sprint 1", 5L))
        );

        InputStreamResource result = csvDownloadService.generateCsv(1L, 1L, "cardsporsprint");
        BufferedReader reader = new BufferedReader(new InputStreamReader(result.getInputStream()));

        assertEquals("\"Sprint\",\"Quantidade\"", reader.readLine());
        assertEquals("\"Sprint 1\",\"5\"", reader.readLine());
    }

    @Test
    void shouldGenerateCsvForCardsporperiodo() throws Exception {
        when(fatoUserStoryTemporaisService.getUserStoriesByPeriodoAndUser(1L, 1L)).thenReturn(
            List.of(new ResponseQuantidadeCardsByPeriodo("2024-01", 3, 2))
        );

        InputStreamResource result = csvDownloadService.generateCsv(1L, 1L, "cardsporperiodo");
        BufferedReader reader = new BufferedReader(new InputStreamReader(result.getInputStream()));

        assertEquals("\"Período\",\"Criadas\",\"Finalizadas\"", reader.readLine());
        assertEquals("\"2024-01\",\"3\",\"2\"", reader.readLine());
    }

    @Test
    void shouldThrowForInvalidExportType() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
            () -> csvDownloadService.generateCsv(1L, 1L, "invalido"));
        assertEquals("Tipo de exportação inválido: invalido", ex.getMessage());
    }
}
