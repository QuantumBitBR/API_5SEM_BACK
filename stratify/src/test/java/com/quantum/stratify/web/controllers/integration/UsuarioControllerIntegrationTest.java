package com.quantum.stratify.web.controllers.integration;

import com.quantum.stratify.entities.Usuario;
import com.quantum.stratify.enums.Role;
import com.quantum.stratify.services.UsuarioService;
import com.quantum.stratify.web.dtos.*;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Testcontainers
public class UsuarioControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UsuarioService usuarioService;

    @Test
    public void testAtivarUsuario() throws Exception {
        Mockito.doNothing().when(usuarioService).ativarUsuario(1L);

        mockMvc.perform(put("/usuario/1/ativar"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testDesativarUsuario() throws Exception {
        Mockito.doNothing().when(usuarioService).desativarUsuario(1L);

        mockMvc.perform(put("/usuario/1/desativar"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testAtribuirLiderados() throws Exception {
        Mockito.doNothing().when(usuarioService).atribuirLideradosAoGestor(Mockito.any());

        mockMvc.perform(post("/usuario/lideradosgestor")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"gestorId\": 1, \"liderados\": [2,3,4]}"))
                .andExpect(status().isOk());
    }

    @Test
    public void testAlterarRoleUsuario() throws Exception {
        Usuario usuarioMock = new Usuario();
        usuarioMock.setId(1L);
        usuarioMock.setNome("Teste");

        Mockito.when(usuarioService.alterarRole(Mockito.eq(1L), Mockito.any()))
                .thenReturn(usuarioMock);

        mockMvc.perform(put("/usuario/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"role\": \"GESTOR\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nome").value("Teste"));
    }

    @Test
    public void testFiltrarUsuarios() throws Exception {
        Mockito.when(usuarioService.buscarUsuariosPorProjetoEGestor(Mockito.any(), Mockito.any()))
                .thenReturn(List.of(new UsuarioDTO(1L, "Usuário 1")));

        mockMvc.perform(get("/usuario/filtrarprojetogestor")
                .param("idProjeto", "1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idUsuario").value(1))
                .andExpect(jsonPath("$[0].nomeUsuario").value("Usuário 1"));
    }

    @Test
public void testListarPorRole() throws Exception {
    Mockito.when(usuarioService.listarPorRole(Role.GESTOR))
           .thenReturn(List.of(new UsuarioPorRoleDTO(1L, "Gestor 1", "gestor@email.com", Role.GESTOR, true, "Gestor Líder")));

    mockMvc.perform(get("/usuario/por-role/GESTOR")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].id").value(1))
            .andExpect(jsonPath("$[0].nome").value("Gestor 1"))
            .andExpect(jsonPath("$[0].email").value("gestor@email.com"))
            .andExpect(jsonPath("$[0].role").value("GESTOR"))
            .andExpect(jsonPath("$[0].isEnable").value(true))
            .andExpect(jsonPath("$[0].gestorNome").value("Gestor Líder"));
}

@Test
public void testResetarSenha() throws Exception {
    
    Mockito.when(usuarioService.resetarSenha(Mockito.anyLong(), Mockito.any()))
       .thenReturn(new Usuario());

   
    mockMvc.perform(put("/usuario/resetar-senha")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"idUsuario\": 1, \"novaSenha\": \"G@briel1234\"}"))
            .andExpect(status().isOk()) 
            .andExpect(content().string("Senha resetada com sucesso.")); 
}
    @Test
public void testListarUsuariosInfo() throws Exception {
    Mockito.when(usuarioService.listarUsuariosInfo())
           .thenReturn(List.of(new UsuarioInfoDTO(1L, "user@email.com", "Gestor Líder", Role.USER, false, true)));

    mockMvc.perform(get("/usuario/listar")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].id").value(1))
            .andExpect(jsonPath("$[0].email").value("user@email.com"))
            .andExpect(jsonPath("$[0].gestorNome").value("Gestor Líder"))
            .andExpect(jsonPath("$[0].role").value("USER"))
            .andExpect(jsonPath("$[0].requireReset").value(false))
            .andExpect(jsonPath("$[0].isEnable").value(true));
}

    @Test
    public void testResetarSenhaAdmin() throws Exception {
        Mockito.doNothing().when(usuarioService).resetarSenhaAdmin(Mockito.any());

        mockMvc.perform(post("/usuario/admin-reset-senha")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"idUsuario\": 1}"))
                .andExpect(status().isOk())
                .andExpect(content().string("Senha resetada e enviada com sucesso."));
    }
}