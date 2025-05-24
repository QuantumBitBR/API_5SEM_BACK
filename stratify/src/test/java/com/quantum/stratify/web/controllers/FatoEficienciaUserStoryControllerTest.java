package com.quantum.stratify.web.controllers;

import com.quantum.stratify.services.FatoEficienciaUserStoryService;
import com.quantum.stratify.web.dtos.FatoEficienciaTempoMedioGeralDTO;
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
                new TempoMedioPorProjetoDTO(10L, "UserStory A", 5.0,1L),
                new TempoMedioPorProjetoDTO(11L, "UserStory B", 6.0,1L)
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
                new TempoMedioPorProjetoDTO(20L, "UserStory C", 7.0,1L)
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


    @Test
    public void testGetTempoMedioPorProjetoFiltrado_somenteUsuario() {
        Long usuarioId = 2L;
        List<TempoMedioPorProjetoDTO> mockList = Arrays.asList(
                new TempoMedioPorProjetoDTO(30L, "UserStory D", 8.5,1L)
        );

        when(service.getTempoMedioFiltrado(null, usuarioId)).thenReturn(mockList);

        ResponseEntity<List<TempoMedioPorProjetoDTO>> response =
                controller.getTempoMedioPorProjetoFiltrado(null, usuarioId);

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());

        verify(service, times(1)).getTempoMedioFiltrado(null, usuarioId);
        System.out.println("✅ testGetTempoMedioPorProjetoFiltrado_somenteUsuario passou!");
    }

    @Test
    public void testGetTempoMedioPorProjetoFiltrado_semParametros() {
        List<TempoMedioPorProjetoDTO> mockList = Arrays.asList(
                new TempoMedioPorProjetoDTO(40L, "UserStory E", 4.2,1L)
        );

        when(service.getTempoMedioFiltrado(null, null)).thenReturn(mockList);

        ResponseEntity<List<TempoMedioPorProjetoDTO>> response =
                controller.getTempoMedioPorProjetoFiltrado(null, null);

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());

        verify(service, times(1)).getTempoMedioFiltrado(null, null);
        System.out.println("✅ testGetTempoMedioPorProjetoFiltrado_semParametros passou!");
    }

    @Test
    public void testGetMediaTempoFiltrado_comFiltros() {
        Long projetoId = 1L;
        Long usuarioId = 2L;
        double mediaEsperada = 6.75;

        when(service.getMediaTempoFiltrado(projetoId, usuarioId)).thenReturn(mediaEsperada);

        ResponseEntity<FatoEficienciaTempoMedioGeralDTO> response = controller.getMediaTempoComFiltro(projetoId, usuarioId);

        FatoEficienciaTempoMedioGeralDTO resultado = controller.getMediaTempoComFiltro(projetoId, usuarioId).getBody();
        assertEquals(mediaEsperada, resultado.tempoMedio());

        System.out.println("✅ testGetMediaTempoFiltrado_comFiltros passou!");
    }

    @Test
    public void testGetMediaTempoFiltrado_somenteProjeto() {
        Long projetoId = 1L;
        double mediaEsperada = 5.5;

        when(service.getMediaTempoFiltrado(projetoId, null)).thenReturn(mediaEsperada);

        ResponseEntity<FatoEficienciaTempoMedioGeralDTO> response = controller.getMediaTempoComFiltro(projetoId, null);

        FatoEficienciaTempoMedioGeralDTO resultado = controller.getMediaTempoComFiltro(projetoId, null).getBody();
        assertEquals(mediaEsperada, resultado.tempoMedio());

        System.out.println(mediaEsperada + " == " + resultado.tempoMedio());
    }

    @Test
    public void testGetMediaTempoFiltrado_semParametros() {
        double mediaEsperada = 7.25;

        when(service.getMediaTempoFiltrado(null, null)).thenReturn(mediaEsperada);

        ResponseEntity<FatoEficienciaTempoMedioGeralDTO> response = controller.getMediaTempoComFiltro(null, null);

        FatoEficienciaTempoMedioGeralDTO resultado = controller.getMediaTempoComFiltro(null, null).getBody();
        assertEquals(mediaEsperada, resultado.tempoMedio());

        System.out.println("✅ testGetMediaTempoFiltrado_semParametros passou!");
    }
}
