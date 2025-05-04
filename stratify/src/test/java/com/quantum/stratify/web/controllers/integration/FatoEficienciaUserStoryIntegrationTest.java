package com.quantum.stratify.web.controllers.integration;

import com.quantum.stratify.services.FatoEficienciaUserStoryService;
import com.quantum.stratify.web.dtos.TempoMedioPorProjetoDTO;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Testcontainers
public class FatoEficienciaUserStoryIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FatoEficienciaUserStoryService fatoEficienciaUserStoryService;

    @Test
    public void testGetTempoMedioPorProjeto() throws Exception {
        
        Mockito.when(fatoEficienciaUserStoryService.getTempoMedioPorProjeto(Mockito.any(), Mockito.any()))
               .thenReturn(List.of(new TempoMedioPorProjetoDTO(1L, "UserStory Teste", 5L)));

        mockMvc.perform(get("/fatoeficiencia/projeto-tempo-medio-us")
                .param("projetoId", "1")
                .param("usuarioId", "10")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].tempoMedio").value(5L))
                .andExpect(jsonPath("$[0].descricao").value("UserStory Teste"));
    }

    @Test
    public void testGetTempoMedioPorProjetoFiltrado() throws Exception {
        Mockito.when(fatoEficienciaUserStoryService.getTempoMedioFiltrado(Mockito.any(), Mockito.any()))
               .thenReturn(List.of(new TempoMedioPorProjetoDTO(2L, "Outra UserStory Teste", 4L)));

        mockMvc.perform(get("/fatoeficiencia/projeto")
                .param("projetoId", "2")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].tempoMedio").value(4L))
                .andExpect(jsonPath("$[0].descricao").value("Outra UserStory Teste"));
    }

    @Test
    public void testGetMediaTempoSemFiltro() throws Exception {
        
        Mockito.when(fatoEficienciaUserStoryService.getMediaTempoFiltrado(Mockito.isNull(), Mockito.isNull()))
               .thenReturn(6.3);
    
        mockMvc.perform(get("/fatoeficiencia/media-tempo")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tempoMedio").value(6.3));
    }
}