package com.quantum.stratify.web.controllers.integration;

import com.quantum.stratify.services.UserStoryService;
import com.quantum.stratify.services.UserStoryStatusService;
import com.quantum.stratify.services.UserStoryTagService;
import com.quantum.stratify.web.dtos.PercentualStatusUsuarioDTO;
import com.quantum.stratify.web.dtos.QuantidadeCardsPorTagDTO;
import com.quantum.stratify.web.dtos.TotalCardsDTO;
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
public class UserStoryControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserStoryService userStoryService;

    @MockBean
    private UserStoryTagService userStoryTagService;

    @MockBean
    private UserStoryStatusService userStoryStatusService;

    @Test
    public void testGetTotalCardCount() throws Exception {
        
        Mockito.when(userStoryService.getTotalCardCount(Mockito.any(), Mockito.any()))
               .thenReturn(new TotalCardsDTO(50L));

        mockMvc.perform(get("/userStory/total-cards")
                .param("idProjeto", "1")
                .param("idUsuario", "10")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.quantidadeUserStories").value(50));
    }

    @Test
    public void testGetQuantidadePorTag() throws Exception {
       
        Mockito.when(userStoryTagService.getQuantidadeUserStoriesByTag(Mockito.any(), Mockito.any()))
               .thenReturn(List.of(new QuantidadeCardsPorTagDTO("Bug", 5L), new QuantidadeCardsPorTagDTO("Feature", 3L)));

        mockMvc.perform(get("/userStory/quantidade-por-tag")
                .param("projetoId", "2")
                .param("usuarioId", "15")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nomeTag").value("Bug"))
                .andExpect(jsonPath("$[0].quantidade").value(5))
                .andExpect(jsonPath("$[1].nomeTag").value("Feature"))
                .andExpect(jsonPath("$[1].quantidade").value(3));
    }

    @Test
    public void testGetPercentualPorStatus() throws Exception {
        // Simula retorno do serviço
        Mockito.when(userStoryStatusService.getPercentualUserStoriesPorStatus(Mockito.any(), Mockito.any()))
               .thenReturn(List.of(new PercentualStatusUsuarioDTO("Concluído", 70.0), new PercentualStatusUsuarioDTO("Em andamento", 30.0)));

        mockMvc.perform(get("/userStory/percentual-por-status")
                .param("projetoId", "3")
                .param("usuarioId", "20")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nomeStatus").value("Concluído"))
                .andExpect(jsonPath("$[0].percentual").value(70.0))
                .andExpect(jsonPath("$[1].nomeStatus").value("Em andamento"))
                .andExpect(jsonPath("$[1].percentual").value(30.0));
    }
}