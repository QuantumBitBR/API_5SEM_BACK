package com.quantum.stratify.web.controllers;

import com.quantum.stratify.entities.Usuario;
import com.quantum.stratify.enums.Role;
import com.quantum.stratify.services.UsuarioService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UsuarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UsuarioService usuarioService;

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void deveRetornarListaDeUsuariosComStatus200() throws Exception {
        List<Usuario> usuarios = List.of(
                new Usuario(111L, "João", "joao@email.com", "aaa", Role.USER, true, false, null),
                new Usuario(222L, "Maria", "maria@email.com", "bbb", Role.ADMIN, true, false, null)
        );
        Mockito.when(usuarioService.buscarTodos()).thenReturn(usuarios);

        mockMvc.perform(get("/api/v1/usuarios"))
                .andDo(print()) // Adiciona o print para inspecionar a resposta
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].email").value("joao@email.com"))
                .andExpect(jsonPath("$[1].email").value("maria@email.com"));
    }
    @Test
    void deveRetornar401SemUsuarioLogado() throws Exception {
        mockMvc.perform(get("/api/v1/usuarios"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    void deveRetornar403ParaUsuarioSemPermissao() throws Exception {
        mockMvc.perform(get("/api/v1/usuarios"))
                .andExpect(status().isForbidden());
    }


}
