package com.quantum.stratify.web.controllers.integration;

import com.quantum.stratify.entities.Periodo;
import com.quantum.stratify.services.PeriodoService;
import com.quantum.stratify.web.dtos.PeriodoDTO;
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
public class PeriodoControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PeriodoService periodoService;

    @Test
    public void testGetAllPeriods() throws Exception {
       
        Mockito.when(periodoService.getAll())
               .thenReturn(List.of(new PeriodoDTO(1L, "Janeiro"), new PeriodoDTO(2L, "Fevereiro")));

        mockMvc.perform(get("/periodo")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].nome").value("Janeiro"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].nome").value("Fevereiro"));
    }

    @Test
public void testGetPeriodById() throws Exception {
   
    Periodo periodoMock = Mockito.mock(Periodo.class);
    Mockito.when(periodoMock.getId()).thenReturn(1L);
    Mockito.when(periodoMock.getNome()).thenReturn("Janeiro");

    
    Mockito.when(periodoService.getById(1L)).thenReturn(periodoMock);

    mockMvc.perform(get("/periodo/1")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.nome").value("Janeiro"));
}
       
    @Test
    public void testSearchPeriodsByName() throws Exception {
        
        Mockito.when(periodoService.findByNomeContaining("Jan"))
               .thenReturn(List.of(new PeriodoDTO(1L, "Janeiro")));

        mockMvc.perform(get("/periodo/buscar?termo=Jan")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].nome").value("Janeiro"));
    }
}