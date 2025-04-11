package com.quantum.stratify.services;

import com.quantum.stratify.entities.FatoUserStoryTemporais;
import com.quantum.stratify.entities.Periodo;
import com.quantum.stratify.entities.Projeto;
import com.quantum.stratify.entities.Usuario;
import com.quantum.stratify.repositories.FatoUserStoryTemporaisRepository;
import com.quantum.stratify.web.dtos.ResponseQuantidadeCardsByPeriodo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

class FatoUserStoryTemporaisServiceTest {

    @Mock
    private FatoUserStoryTemporaisRepository fatoUserStoryTemporaisRepository;

    @Mock
    private ProjetoService projetoService;

    @Mock
    private UsuarioService usuarioService;

    @InjectMocks
    private FatoUserStoryTemporaisService fatoUserStoryTemporaisService;

    private Projeto projetoMock;
    private Usuario usuarioMock;
    private Periodo periodoMock1;
    private Periodo periodoMock2;
    private FatoUserStoryTemporais fato1;
    private FatoUserStoryTemporais fato2;
    private FatoUserStoryTemporais fato3;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        projetoMock = new Projeto();
        projetoMock.setId(1L);

        usuarioMock = new Usuario();
        usuarioMock.setId(2L);

        periodoMock1 = new Periodo();
        periodoMock1.setNome("Semana 1");

        periodoMock2 = new Periodo();
        periodoMock2.setNome("Semana 2");

        fato1 = new FatoUserStoryTemporais();
        fato1.setProjeto(projetoMock);
        fato1.setPeriodo(periodoMock1);
        fato1.setQuantidadeUserStoriesCriadas(5);
        fato1.setQuantidadeUserStoriesFinalizadas(2);

        fato2 = new FatoUserStoryTemporais();
        fato2.setProjeto(projetoMock);
        fato2.setPeriodo(periodoMock1);
        fato2.setQuantidadeUserStoriesCriadas(3);
        fato2.setQuantidadeUserStoriesFinalizadas(4);

        fato3 = new FatoUserStoryTemporais();
        fato3.setProjeto(projetoMock);
        fato3.setUsuario(usuarioMock);
        fato3.setPeriodo(periodoMock2);
        fato3.setQuantidadeUserStoriesCriadas(2);
        fato3.setQuantidadeUserStoriesFinalizadas(3);
    }

    @Test
    void deveRetornarDadosAgrupadosPorPeriodoQuandoBuscarPorProjeto() {
        // Arrange
        when(projetoService.getById(1L)).thenReturn(projetoMock);
        when(fatoUserStoryTemporaisRepository.findByProjeto(projetoMock)).thenReturn(Arrays.asList(fato1, fato2));

        // Act
        List<ResponseQuantidadeCardsByPeriodo> resultado = fatoUserStoryTemporaisService.getUserStoriesByPeriodoAndUser(1L, null);

        // Assert
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Semana 1", resultado.get(0).periodo());
        assertEquals(8, resultado.get(0).quantidadeCriadas());
        assertEquals(6, resultado.get(0).quantidadeFinalizadas());
    }

    @Test
    void deveRetornarListaComMensagemDeNenhumRegistroQuandoBuscarPorProjetoENaoEncontrarDados() {
        // Arrange
        when(projetoService.getById(1L)).thenReturn(projetoMock);
        when(fatoUserStoryTemporaisRepository.findByProjeto(projetoMock)).thenReturn(List.of());

        // Act
        List<ResponseQuantidadeCardsByPeriodo> resultado = fatoUserStoryTemporaisService.getUserStoriesByPeriodoAndUser(1L, null);

        // Assert
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Nenhum Registro Encontrado", resultado.get(0).periodo());
        assertEquals(0, resultado.get(0).quantidadeCriadas());
        assertEquals(0, resultado.get(0).quantidadeFinalizadas());
    }

    @Test
    void deveRetornarDadosAgrupadosPorPeriodoQuandoBuscarPorProjetoEUsuario() {
        // Arrange
        when(projetoService.getById(1L)).thenReturn(projetoMock);
        when(usuarioService.getById(2L)).thenReturn(usuarioMock);
        when(fatoUserStoryTemporaisRepository.findByProjetoAndUsuario(projetoMock, usuarioMock)).thenReturn(Arrays.asList(fato3));

        // Act
        List<ResponseQuantidadeCardsByPeriodo> resultado = fatoUserStoryTemporaisService.getUserStoriesByPeriodoAndUser(1L, 2L);

        // Assert
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Semana 2", resultado.get(0).periodo());
        assertEquals(2, resultado.get(0).quantidadeCriadas());
        assertEquals(3, resultado.get(0).quantidadeFinalizadas());
    }

    @Test
    void deveRetornarListaComMensagemDeNenhumRegistroQuandoBuscarPorProjetoEUsuarioENaoEncontrarDados() {
        // Arrange
        when(projetoService.getById(1L)).thenReturn(projetoMock);
        when(usuarioService.getById(2L)).thenReturn(usuarioMock);
        when(fatoUserStoryTemporaisRepository.findByProjetoAndUsuario(projetoMock, usuarioMock)).thenReturn(List.of());

        // Act
        List<ResponseQuantidadeCardsByPeriodo> resultado = fatoUserStoryTemporaisService.getUserStoriesByPeriodoAndUser(1L, 2L);

        // Assert
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Nenhum Registro Encontrado", resultado.get(0).periodo());
        assertEquals(0, resultado.get(0).quantidadeCriadas());
        assertEquals(0, resultado.get(0).quantidadeFinalizadas());
    }
}