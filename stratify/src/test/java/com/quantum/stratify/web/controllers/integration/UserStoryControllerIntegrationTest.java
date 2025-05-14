package com.quantum.stratify.web.controllers.integration;



import com.quantum.stratify.services.UserStoryService;
import com.quantum.stratify.services.UserStoryStatusService;
import com.quantum.stratify.services.UserStoryTagService;
import com.quantum.stratify.web.dtos.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class UserStoryControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserStoryService userStoryService;

    @MockBean
    private UserStoryTagService userStoryTagService;

    @MockBean
    private UserStoryStatusService userStoryStatusService;

    @BeforeEach
    void setUp() {
        Mockito.reset(userStoryService, userStoryTagService, userStoryStatusService);
    }

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
               .thenReturn(List.of(
                   new QuantidadeCardsPorTagDTO("Bug", 5L),
                   new QuantidadeCardsPorTagDTO("Feature", 3L)
               ));

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
}
