package com.quantum.stratify.services;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import com.quantum.stratify.StratifyApplication;






@Testcontainers
@SpringBootTest
@ContextConfiguration(classes = StratifyApplication.class)
public class DatabaseIntegrationTest {

    @Container
    public static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:latest")
            .withDatabaseName("testdb")
            .withUsername("user")
            .withPassword("password");

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    public void testDatabaseConnection() {
        Integer result = jdbcTemplate.queryForObject("SELECT 1", Integer.class);
        assertEquals(1, result);
    }
    @Test
    public void testInsertAndQuery() {
        jdbcTemplate.update("INSERT INTO dim_usuario (nome, email, senha) VALUES (?, ?, ?)", 
                            "Gabriel", "gabriel@email.com", "senha123");

        Integer count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM dim_usuario WHERE email = ?", 
                                                     Integer.class, "gabriel@email.com");
        
        assertEquals(1, count);
    }

}