package com.quantum.stratify.web.controllers;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.quantum.stratify.services.UserStoryService;
import com.quantum.stratify.services.UserStoryStatusService;
import com.quantum.stratify.services.UserStoryTagService;
import com.quantum.stratify.web.dtos.PercentualStatusUsuarioDTO;

@WebMvcTest(UserStoryController.class)
class UserStoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserStoryService userStoryService;

    @MockBean
    private UserStoryTagService userStoryTagService;

    @MockBean
    private UserStoryStatusService userStoryStatusService;

    private List<PercentualStatusUsuarioDTO> getMockResponse() {
        return Arrays.asList(
            new PercentualStatusUsuarioDTO("TO DO", 60.0),
            new PercentualStatusUsuarioDTO("DONE", 40.0)
        );
    }

    @Test
    @DisplayName("Deve retornar 200 e lista de percentuais sem filtros")
    @WithMockUser(username = "mockUser", roles = {"ADMIN"})
    void testPercentualSemParametros() throws Exception {
        List<PercentualStatusUsuarioDTO> mockResponse = getMockResponse();
        when(userStoryStatusService.getPercentualUserStoriesPorStatus(null, null))
            .thenReturn(mockResponse);

        mockMvc.perform(get("/userStory/percentual-por-status"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].nomeStatus").value("TO DO"))
            .andExpect(jsonPath("$[0].percentual").value(60.0))
            .andExpect(jsonPath("$[1].nomeStatus").value("DONE"))
            .andExpect(jsonPath("$[1].percentual").value(40.0));
    }

    @Test
    @DisplayName("Deve retornar percentuais com projetoId")
    @WithMockUser(username = "mockUser", roles = {"ADMIN"})
    void testPercentualComProjetoId() throws Exception {
        Long projetoId = 1L;
        List<PercentualStatusUsuarioDTO> mockResponse = getMockResponse();
        when(userStoryStatusService.getPercentualUserStoriesPorStatus(projetoId, null))
            .thenReturn(mockResponse);

        mockMvc.perform(get("/userStory/percentual-por-status")
                .param("projetoId", projetoId.toString()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].nomeStatus").value("TO DO"))
            .andExpect(jsonPath("$[0].percentual").value(60.0));
    }

    @Test
    @DisplayName("Deve retornar percentuais com usuarioId")
    @WithMockUser(username = "mockUser", roles = {"ADMIN"})
    void testPercentualComUsuarioId() throws Exception {
        Long usuarioId = 42L;
        List<PercentualStatusUsuarioDTO> mockResponse = getMockResponse();
        when(userStoryStatusService.getPercentualUserStoriesPorStatus(null, usuarioId))
            .thenReturn(mockResponse);

        mockMvc.perform(get("/userStory/percentual-por-status")
                .param("usuarioId", usuarioId.toString()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[1].nomeStatus").value("DONE"))
            .andExpect(jsonPath("$[1].percentual").value(40.0));
    }

    @Test
    @DisplayName("Deve retornar percentuais com projetoId e usuarioId")
    @WithMockUser(username = "mockUser", roles = {"ADMIN"})
    void testPercentualComProjetoEUsuario() throws Exception {
        Long projetoId = 1L;
        Long usuarioId = 42L;
        List<PercentualStatusUsuarioDTO> mockResponse = getMockResponse();
        when(userStoryStatusService.getPercentualUserStoriesPorStatus(projetoId, usuarioId))
            .thenReturn(mockResponse);

        mockMvc.perform(get("/userStory/percentual-por-status")
                .param("projetoId", projetoId.toString())
                .param("usuarioId", usuarioId.toString()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()").value(2))
            .andExpect(jsonPath("$[0].nomeStatus").value("TO DO"))
            .andExpect(jsonPath("$[1].nomeStatus").value("DONE"));
    }
}
