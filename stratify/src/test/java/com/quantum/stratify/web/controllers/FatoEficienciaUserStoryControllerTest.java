package com.quantum.stratify.web.controllers;

import com.quantum.stratify.services.FatoEficienciaUserStoryService;
import com.quantum.stratify.web.dtos.TempoMedioPorProjetoDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class FatoEficienciaUserStoryControllerTest {

    private FatoEficienciaUserStoryService service;
    private FatoEficienciaUserStoryController controller;

    @BeforeEach
    public void setup() {
        service = mock(FatoEficienciaUserStoryService.class);
        controller = new FatoEficienciaUserStoryController();
        controller.fatoEficienciaUserStoryService = service;
    }

    @Test
    public void testGetTempoMedioPorProjetoFiltrado_comUsuario() {
        Long projetoId = 1L;
        Long usuarioId = 2L;
        List<TempoMedioPorProjetoDTO> mockList = Arrays.asList(
                new TempoMedioPorProjetoDTO(10L, "UserStory A", 5.0),
                new TempoMedioPorProjetoDTO(11L, "UserStory B", 6.0)
        );

        when(service.getTempoMedioFiltrado(projetoId, usuarioId)).thenReturn(mockList);

        ResponseEntity<List<TempoMedioPorProjetoDTO>> response =
                controller.getTempoMedioPorProjetoFiltrado(projetoId, usuarioId);

        assertEquals(200, response.getStatusCodeValue(), "Código de resposta deveria ser 200 (OK)");
        assertNotNull(response.getBody(), "Corpo da resposta não deveria ser nulo");
        assertEquals(2, response.getBody().size(), "Deveria retornar exatamente 2 user stories");

        verify(service, times(1)).getTempoMedioFiltrado(projetoId, usuarioId);
        System.out.println("✅ testGetTempoMedioPorProjetoFiltrado_comUsuario passou!");
    }

    @Test
    public void testGetTempoMedioPorProjetoFiltrado_semUsuario() {
        Long projetoId = 1L;
        List<TempoMedioPorProjetoDTO> mockList = Arrays.asList(
                new TempoMedioPorProjetoDTO(20L, "UserStory C", 7.0)
        );

        when(service.getTempoMedioFiltrado(projetoId, null)).thenReturn(mockList);

        ResponseEntity<List<TempoMedioPorProjetoDTO>> response =
                controller.getTempoMedioPorProjetoFiltrado(projetoId, null);

        assertEquals(200, response.getStatusCodeValue(), "Código de resposta deveria ser 200 (OK)");
        assertNotNull(response.getBody(), "Corpo da resposta não deveria ser nulo");
        assertEquals(1, response.getBody().size(), "Deveria retornar exatamente 1 user story");

        verify(service, times(1)).getTempoMedioFiltrado(projetoId, null);
        System.out.println("✅ testGetTempoMedioPorProjetoFiltrado_semUsuario passou!");
    }
}
