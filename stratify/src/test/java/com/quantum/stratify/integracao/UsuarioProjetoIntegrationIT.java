package com.quantum.stratify.integracao;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

import com.quantum.stratify.entities.Projeto;
import com.quantum.stratify.entities.Usuario;
import com.quantum.stratify.services.ProjetoService;
import com.quantum.stratify.services.UsuarioService;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Testcontainers
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UsuarioProjetoIntegrationIT {

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15-alpine")
            .withDatabaseName("testdb")
            .withUsername("testuser")
            .withPassword("testpass");

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        // Configurações do banco de dados
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
        registry.add("spring.datasource.driver-class-name", () -> "org.postgresql.Driver");
        
        // Desabilita o Liquibase para testes
        registry.add("spring.liquibase.enabled", () -> "false");
        
        // Configura o Hibernate para criar o schema
        registry.add("spring.jpa.hibernate.ddl-auto", () -> "create-drop");
        registry.add("spring.jpa.properties.hibernate.dialect", () -> "org.hibernate.dialect.PostgreSQLDialect");
        registry.add("spring.jpa.show-sql", () -> "true");
    }

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private ProjetoService projetoService;

    @Test
    @Order(1)
    @DisplayName("Deve carregar o contexto da aplicação com sucesso")
    void contextLoads() {
        assertNotNull(usuarioService);
        assertNotNull(projetoService);
    }

    @Test
    @Order(2)
    @DisplayName("Deve buscar usuário por email com sucesso")
    void deveBuscarUsuarioPorEmail() {
        // Configuração inicial dos dados pode ser feita aqui ou em um método @BeforeEach
        Usuario usuario = usuarioService.buscarPorEmail("admin@example.com");
        assertNotNull(usuario, "Usuário não encontrado");
        assertEquals("admin@example.com", usuario.getEmail());
    }

    // Outros testes...
}