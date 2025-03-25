package com.quantum.stratify;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.quantum.stratify.entities.FatoTagUserStory;
import com.quantum.stratify.entities.Projeto;
import com.quantum.stratify.entities.Tag;
import com.quantum.stratify.repositories.FatoProgressoRepository;
import com.quantum.stratify.services.FatoProgressoService;
import com.quantum.stratify.services.ProjetoService;
import com.quantum.stratify.web.dtos.ResponseQuantidadeCardsByTags;

@ExtendWith(MockitoExtension.class)
public class FatoProgressoServiceTest {

    @Mock
    private FatoProgressoRepository fatoProgressoRepository;

    @Mock
    private ProjetoService projetoService;

    @InjectMocks
    private FatoProgressoService fatoProgressoService;

    @Test
    void getQuantidadeUserStoriesByTagShouldReturnListWhenProjetoIdIsNotNull() {
        // Arrange
        Long projetoId = 1L;
        Projeto projeto = new Projeto();
        projeto.setId(projetoId);

        Tag tag = new Tag();
        tag.setId(1L);
        tag.setNome("Tag Teste");

        FatoTagUserStory FatoTagUserStory = new FatoTagUserStory();
        FatoTagUserStory.setTag(tag);
        FatoTagUserStory.setProjeto(projeto);
        FatoTagUserStory.setQuantidadeUserStories(10);

        when(projetoService.getById(projetoId)).thenReturn(projeto);
        when(fatoProgressoRepository.findByProjeto(projeto)).thenReturn(Arrays.asList(FatoTagUserStory));

        // Act
        List<ResponseQuantidadeCardsByTags> resultados = fatoProgressoService.getQuantidadeUserStoriesByTag(projetoId);

        // Assert
        assertEquals(1, resultados.size());
        assertEquals(1L, resultados.get(0).idTag());
        assertEquals("Tag Teste", resultados.get(0).nomeTag());
        assertEquals(projetoId, resultados.get(0).idProjeto());
        assertEquals(10, resultados.get(0).quantidadeCards());
        verify(projetoService, times(1)).getById(projetoId);
        verify(fatoProgressoRepository, times(1)).findByProjeto(projeto);
    }

    @Test
    void getQuantidadeUserStoriesByTagShouldReturnListWhenProjetoIdIsNull() {
        // Arrange
        Tag tag = new Tag();
        tag.setId(1L);
        tag.setNome("Tag Teste");

        Projeto projeto = new Projeto();
        projeto.setId(1L);

        FatoTagUserStory FatoTagUserStory = new FatoTagUserStory();
        FatoTagUserStory.setTag(tag);
        FatoTagUserStory.setProjeto(projeto);
        FatoTagUserStory.setQuantidadeUserStories(10);

        when(fatoProgressoRepository.findAll()).thenReturn(Arrays.asList(FatoTagUserStory));

        // Act
        List<ResponseQuantidadeCardsByTags> resultados = fatoProgressoService.getQuantidadeUserStoriesByTag(null);

        // Assert
        assertEquals(1, resultados.size());
        assertEquals(1L, resultados.get(0).idTag());
        assertEquals("Tag Teste", resultados.get(0).nomeTag());
        assertEquals(1L, resultados.get(0).idProjeto());
        assertEquals(10, resultados.get(0).quantidadeCards());
        verify(fatoProgressoRepository, times(1)).findAll();
    }

    @Test
    void getAllShouldReturnListOfFatoTagUserStory() {
        // Arrange
        FatoTagUserStory FatoTagUserStory = new FatoTagUserStory();
        FatoTagUserStory.setId(1L);

        when(fatoProgressoRepository.findAll()).thenReturn(Arrays.asList(FatoTagUserStory));

        // Act
        List<FatoTagUserStory> resultados = fatoProgressoService.getAll();

        // Assert
        assertEquals(1, resultados.size());
        assertEquals(1L, resultados.get(0).getId());
        verify(fatoProgressoRepository, times(1)).findAll();
    }

    @Test
    void getTotalCardCountShouldReturnTotalCount() {
        // Arrange
        when(fatoProgressoRepository.countTotalCards()).thenReturn(5L);

        // Act
        Long totalCards = fatoProgressoService.getTotalCardCount();

        // Assert
        assertEquals(5L, totalCards);
        verify(fatoProgressoRepository, times(1)).countTotalCards();
    }
}