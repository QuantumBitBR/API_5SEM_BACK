package com.quantum.stratify.web.controllers.integration;



import com.quantum.stratify.entities.Usuario;
import com.quantum.stratify.enums.Role;
import com.quantum.stratify.repositories.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        usuarioRepository.deleteAll();

        Usuario admin = new Usuario();
        admin.setEmail("admin@gmail.com");
        admin.setSenha(passwordEncoder.encode("QuantumBit321!"));
        admin.setNome("Admin");
        admin.setRole(Role.ADMIN);
        admin.setIsEnable(true);
        admin.setRequireReset(false);

        usuarioRepository.save(admin);
    }

    @Test
    public void testAutenticacaoComCredenciaisValidas() throws Exception {
        String loginJson = """
        {
            "email": "admin@gmail.com",
            "senha": "QuantumBit321!"
        }
        """;

        mockMvc.perform(post("/auth")
                .contentType(MediaType.APPLICATION_JSON)
                .content(loginJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").exists())
                .andExpect(jsonPath("$.role").value("ADMIN"));
    }
   /*
    @Test
    public void testAutenticacaoComCredenciaisInvalidas() throws Exception {
        String loginJson = """
        {
            "email": "admin@gmail.com",
            "senha": "senha_incorreta"
        }
        """;

        mockMvc.perform(post("/auth")
                .contentType(MediaType.APPLICATION_JSON)
                .content(loginJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.mensagem").value("Credenciais Inválidas"));
    } */
}