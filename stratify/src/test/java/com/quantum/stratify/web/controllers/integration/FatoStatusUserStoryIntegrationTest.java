package com.quantum.stratify.web.controllers.integration;

import com.quantum.stratify.services.FatoStatusUserStoryService;
import com.quantum.stratify.web.dtos.PercentualStatusDTO;
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
public class FatoStatusUserStoryIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FatoStatusUserStoryService fatoStatusUserStoryService;

    @Test
    public void testGetStatusUserStoryPercentual_Success() throws Exception {
        
        Mockito.when(fatoStatusUserStoryService.getStatusUserStory(Mockito.any()))
               .thenReturn(List.of(
                   new PercentualStatusDTO("Concluído", 70.0),
                   new PercentualStatusDTO("Em andamento", 30.0)
               ));

        mockMvc.perform(get("/fatoStatusUserStory/quantidade-por-status")
                .param("projetoId", "1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].status").value("Concluído"))
                .andExpect(jsonPath("$[0].percentual").value(70.0))
                .andExpect(jsonPath("$[1].status").value("Em andamento"))
                .andExpect(jsonPath("$[1].percentual").value(30.0));
    }

    @Test
    public void testGetStatusUserStoryPercentual_NoData() throws Exception {
        
        Mockito.when(fatoStatusUserStoryService.getStatusUserStory(Mockito.any()))
               .thenReturn(List.of());

        mockMvc.perform(get("/fatoStatusUserStory/quantidade-por-status")
                .param("projetoId", "1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty()); 
    }

    @Test
    public void testGetStatusUserStoryPercentual_BadRequest() throws Exception {
        
        mockMvc.perform(get("/fatoStatusUserStory/quantidade-por-status")
        .param("projetoId", "1")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk()); 
    }

    
}
    