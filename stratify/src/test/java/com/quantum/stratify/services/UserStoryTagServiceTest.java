package com.quantum.stratify.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.quantum.stratify.repositories.UserStoryRepository;
import com.quantum.stratify.web.dtos.QuantidadeCardsPorTagDTO;

class UserStoryTagServiceTest {

    private UserStoryRepository userStoryRepository;
    private UserStoryTagService userStoryTagService;

    @BeforeEach
    void setUp() {
        userStoryRepository = mock(UserStoryRepository.class);
        userStoryTagService = new UserStoryTagService(userStoryRepository);
    }

    @Test
    void deveRetornarUserStoriesFiltradasPorProjetoEUsuario() {
        // Arrange
        Long projetoId = 1L;
        Long usuarioId = 2L;

        List<QuantidadeCardsPorTagDTO> mockResultado = List.of(
            new QuantidadeCardsPorTagDTO("Backend",3L),
            new QuantidadeCardsPorTagDTO("Frontend", 5L)
        );

        when(userStoryRepository.contarUserStoriesPorTag(projetoId, usuarioId)).thenReturn(mockResultado);

        // Act
        List<QuantidadeCardsPorTagDTO> resultado = userStoryTagService.getQuantidadeUserStoriesByTag(projetoId, usuarioId);

        // Assert
        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertEquals("Backend", resultado.get(0).getNomeTag());
        assertEquals(3L, resultado.get(0).getQuantidade());
    }

    @Test
    void deveRetornarListaVaziaQuandoNaoHouverUserStories() {
        // Arrange
        Long projetoId = 99L;
        Long usuarioId = 77L;

        when(userStoryRepository.contarUserStoriesPorTag(projetoId, usuarioId)).thenReturn(List.of());

        // Act
        List<QuantidadeCardsPorTagDTO> resultado = userStoryTagService.getQuantidadeUserStoriesByTag(projetoId, usuarioId);

        // Output visual
        System.out.println("Resultado esperado: lista vazia");
        System.out.println("Lista retornada: " + resultado);

        // Assert
        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
    }
}
