package com.quantum.stratify.web.controllers.integration;

import com.quantum.stratify.services.FatoProgressoService;
import com.quantum.stratify.web.dtos.ResponseQuantidadeCardsByTags;
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
public class FatoProgressoIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FatoProgressoService fatoProgressoService;

    @Test
    public void testGetQuantidadePorEtiqueta() throws Exception {
       
        Mockito.when(fatoProgressoService.getQuantidadeUserStoriesByTag(Mockito.any()))
               .thenReturn(List.of(new ResponseQuantidadeCardsByTags(null, "Bug", null, 5)));

        mockMvc.perform(get("/fatoProgresso/quantidade-por-etiqueta")
                .param("projetoId", "1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
                //.andExpect(jsonPath("$[0].tag").value("Bug")) //necessario popular a tabela 
               // .andExpect(jsonPath("$[0].quantidade").value(5)); //necessario popular a tabela
    }
}