package com.quantum.stratify.web.controllers.integration;


import com.quantum.stratify.services.ProjetoService;
import com.quantum.stratify.web.dtos.ProjetoDTO;
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
public class ProjetoControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProjetoService projetoService;

    @Test
    public void testGetAllProjetos() throws Exception {
      
        Mockito.when(projetoService.getAll())
               .thenReturn(List.of(new ProjetoDTO(1L, "Projeto A"), new ProjetoDTO(2L, "Projeto B")));

        mockMvc.perform(get("/projetos/all")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].nome").value("Projeto A"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].nome").value("Projeto B"));
    }

    @Test
    public void testGetProjetosPorUsuario() throws Exception {
        
        Mockito.when(projetoService.buscarProjetosPorUsuarios(10L))
               .thenReturn(List.of(new ProjetoDTO(3L, "Projeto C")));

        mockMvc.perform(get("/projetos/porUsuario")
                .param("idUsuario", "10")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(3))
                .andExpect(jsonPath("$[0].nome").value("Projeto C"));
    }
}