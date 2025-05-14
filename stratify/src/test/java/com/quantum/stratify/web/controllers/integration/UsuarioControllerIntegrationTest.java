package com.quantum.stratify.web.controllers.integration;




import com.quantum.stratify.enums.Role;
import com.quantum.stratify.services.UsuarioService;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class UsuarioControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UsuarioService usuarioService;

    @BeforeEach
    void setUp() {
        Mockito.reset(usuarioService);
    }

    @Test
    public void testAtivarUsuario() throws Exception {
        Mockito.doNothing().when(usuarioService).ativarUsuario(1L);

        mockMvc.perform(put("/usuario/1/ativar"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testListarPorRole() throws Exception {
        Mockito.when(usuarioService.listarPorRole(Role.GESTOR))
               .thenReturn(List.of(
                   new UsuarioPorRoleDTO(1L, "Gestor 1", "gestor@email.com", Role.GESTOR, true, "Gestor Líder")
               ));

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
    public void testResetarSenhaAdmin() throws Exception {
        Mockito.doNothing().when(usuarioService).resetarSenhaAdmin(Mockito.any());

        mockMvc.perform(post("/usuario/admin-reset-senha")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"idUsuario\": 1}"))
                .andExpect(status().isOk())
                .andExpect(content().string("Senha resetada e enviada com sucesso."));
    }
}
