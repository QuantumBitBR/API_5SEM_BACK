package com.quantum.stratify.web.controllers.integration;

import com.quantum.stratify.services.FatoUserStoryTemporaisService;
import com.quantum.stratify.web.dtos.ResponseQuantidadeCardsByPeriodo;
import com.quantum.stratify.web.exceptions.EntityNotFoundException;
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
public class FatoUserStoryTemporaisControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FatoUserStoryTemporaisService fatoUserStoryTemporaisService;

    @Test
    public void testGetUserStoriesByPeriodoAndUser_Success() throws Exception {
        Mockito.when(fatoUserStoryTemporaisService.getUserStoriesByPeriodoAndUser(Mockito.any(), Mockito.any()))
               .thenReturn(List.of(new ResponseQuantidadeCardsByPeriodo("Jan/2025", 5, 10)));
    
        mockMvc.perform(get("/temporais/projeto")
                .param("idProjeto", "1")
                .param("idUsuario", "10")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].periodo").value("Jan/2025"))
                .andExpect(jsonPath("$[0].quantidadeCriadas").value(5)) 
                .andExpect(jsonPath("$[0].quantidadeFinalizadas").value(10));
    }

    @Test
    public void testGetUserStoriesByPeriodoAndUser_NoData() throws Exception {
       
        Mockito.when(fatoUserStoryTemporaisService.getUserStoriesByPeriodoAndUser(Mockito.any(), Mockito.any()))
               .thenReturn(List.of());

        mockMvc.perform(get("/temporais/projeto")
                .param("idProjeto", "2")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());
    }

   

    @Test
    public void testGetUserStoriesByPeriodoAndUser_EntityNotFound() throws Exception {
        
        Mockito.when(fatoUserStoryTemporaisService.getUserStoriesByPeriodoAndUser(Mockito.any(), Mockito.any()))
               .thenThrow(new EntityNotFoundException("Projeto não encontrado"));

        mockMvc.perform(get("/temporais/projeto")
                .param("idProjeto", "999") 
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Projeto não encontrado"));
    }
}