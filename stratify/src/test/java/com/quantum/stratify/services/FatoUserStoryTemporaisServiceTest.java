package com.quantum.stratify.services;

import com.quantum.stratify.entities.FatoUserStoryTemporais;
import com.quantum.stratify.entities.Periodo;
import com.quantum.stratify.entities.Projeto;
import com.quantum.stratify.entities.Usuario;
import com.quantum.stratify.repositories.FatoUserStoryTemporaisRepository;
import com.quantum.stratify.web.dtos.ResponseQuantidadeCardsByPeriodo;
import com.quantum.stratify.web.exceptions.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FatoUserStoryTemporaisServiceTest {

    @Mock
    private FatoUserStoryTemporaisRepository fatoUserStoryTemporaisRepository;

    @Mock
    private ProjetoService projetoService;

    @Mock
    private UsuarioService usuarioService;

    @InjectMocks
    private FatoUserStoryTemporaisService service;

    private Projeto projeto;
    private Usuario usuario;
    private Periodo periodo1;
    private Periodo periodo2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        projeto = new Projeto();
        projeto.setId(1L);

        usuario = new Usuario();
        usuario.setId(2L);

        periodo1 = new Periodo();
        periodo1.setNome("Sprint 1");

        periodo2 = new Periodo();
        periodo2.setNome("Sprint 2");
    }

    @Test
    void getUserStoriesByPeriodoAndUser_ComUsuarioNull_DeveRetornarDadosAgrupados() {
        // Arrange
        FatoUserStoryTemporais fato1 = criarFato(projeto, null, periodo1, 3, 2);
        FatoUserStoryTemporais fato2 = criarFato(projeto, null, periodo1, 2, 1);
        FatoUserStoryTemporais fato3 = criarFato(projeto, null, periodo2, 5, 3);

        when(projetoService.getById(1L)).thenReturn(projeto);
        when(fatoUserStoryTemporaisRepository.findByProjeto(projeto))
            .thenReturn(Arrays.asList(fato1, fato2, fato3));

        // Act
        List<ResponseQuantidadeCardsByPeriodo> result = service.getUserStoriesByPeriodoAndUser(1L, null);

        // Assert
        assertEquals(2, result.size());
        assertTrue(result.stream().anyMatch(r -> 
            r.periodo().equals("Sprint 1") && 
            r.quantidadeCriadas() == 5 && 
            r.quantidadeFinalizadas() == 3));
        assertTrue(result.stream().anyMatch(r -> 
            r.periodo().equals("Sprint 2") && 
            r.quantidadeCriadas() == 5 && 
            r.quantidadeFinalizadas() == 3));
    }

    @Test
    void getUserStoriesByPeriodoAndUser_ComUsuario_DeveRetornarDadosAgrupados() {
        // Arrange
        FatoUserStoryTemporais fato1 = criarFato(projeto, usuario, periodo1, 4, 3);
        FatoUserStoryTemporais fato2 = criarFato(projeto, usuario, periodo2, 2, 1);

        when(projetoService.getById(1L)).thenReturn(projeto);
        when(usuarioService.getById(2L)).thenReturn(usuario);
        when(fatoUserStoryTemporaisRepository.findByProjetoAndUsuario(projeto, usuario))
            .thenReturn(Arrays.asList(fato1, fato2));

        // Act
        List<ResponseQuantidadeCardsByPeriodo> result = service.getUserStoriesByPeriodoAndUser(1L, 2L);

        // Assert
        assertEquals(2, result.size());
        assertTrue(result.stream().anyMatch(r -> 
            r.periodo().equals("Sprint 1") && 
            r.quantidadeCriadas() == 4 && 
            r.quantidadeFinalizadas() == 3));
        assertTrue(result.stream().anyMatch(r -> 
            r.periodo().equals("Sprint 2") && 
            r.quantidadeCriadas() == 2 && 
            r.quantidadeFinalizadas() == 1));
    }

    @Test
    void getUserStoriesByPeriodoAndUser_ResultadosNull_DeveLancarExcecao() {
        // Arrange
        when(projetoService.getById(1L)).thenReturn(projeto);
        when(fatoUserStoryTemporaisRepository.findByProjeto(projeto)).thenReturn(null);

        // Act & Assert
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            service.getUserStoriesByPeriodoAndUser(1L, null);
        });
        
        assertEquals("Nenhum registro de User Stories encontrado para os parâmetros fornecidos", 
            exception.getMessage());
    }

    @Test
    void getUserStoriesByPeriodoAndUser_ResultadosVazios_DeveLancarExcecao() {
        // Arrange
        when(projetoService.getById(1L)).thenReturn(projeto);
        when(fatoUserStoryTemporaisRepository.findByProjeto(projeto)).thenReturn(Collections.emptyList());

        // Act & Assert
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            service.getUserStoriesByPeriodoAndUser(1L, null);
        });
        
        assertEquals("Nenhum registro de User Stories encontrado para os parâmetros fornecidos", 
            exception.getMessage());
    }

    @Test
    void agruparResultadosPorPeriodo_ComMultiplosPeriodos_DeveAgruparCorretamente() {
        // Arrange
        FatoUserStoryTemporais fato1 = criarFato(projeto, null, periodo1, 3, 2);
        FatoUserStoryTemporais fato2 = criarFato(projeto, null, periodo1, 2, 1);
        FatoUserStoryTemporais fato3 = criarFato(projeto, null, periodo2, 5, 3);

        // Act
        List<ResponseQuantidadeCardsByPeriodo> result = service.agruparResultadosPorPeriodo(
            Arrays.asList(fato1, fato2, fato3));

        // Assert
        assertEquals(2, result.size());
        assertTrue(result.stream().anyMatch(r -> 
            r.periodo().equals("Sprint 1") && 
            r.quantidadeCriadas() == 5 && 
            r.quantidadeFinalizadas() == 3));
        assertTrue(result.stream().anyMatch(r -> 
            r.periodo().equals("Sprint 2") && 
            r.quantidadeCriadas() == 5 && 
            r.quantidadeFinalizadas() == 3));
    }

    private FatoUserStoryTemporais criarFato(Projeto projeto, Usuario usuario, Periodo periodo, 
                                           int criadas, int finalizadas) {
        FatoUserStoryTemporais fato = new FatoUserStoryTemporais();
        fato.setProjeto(projeto);
        fato.setUsuario(usuario);
        fato.setPeriodo(periodo);
        fato.setQuantidadeUserStoriesCriadas(criadas);
        fato.setQuantidadeUserStoriesFinalizadas(finalizadas);
        return fato;
    }
}