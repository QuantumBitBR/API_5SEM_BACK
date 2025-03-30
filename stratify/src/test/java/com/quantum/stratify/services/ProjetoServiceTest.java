package com.quantum.stratify.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import com.quantum.stratify.entities.Projeto;
import com.quantum.stratify.repositories.ProjetoRepository;
import com.quantum.stratify.web.dtos.ProjetoDTO;

@ExtendWith(MockitoExtension.class)
public class ProjetoServiceTest {
    @Mock
    private ProjetoRepository projetoRepository;

    @InjectMocks
    private ProjetoService projetoService;

    @Test
    void getByIdShouldReturnProjetoWhenProjetoExists(){
        Projeto projeto = new Projeto();
        projeto.setId(1L);
        projeto.setNome("Projeto Teste");

        when(projetoRepository.findById(1L)).thenReturn(Optional.of(projeto));

        Projeto resultado = projetoService.getById(1L);

        assertEquals(1L, resultado.getId());
        assertEquals("Projeto Teste", resultado.getNome());
        verify(projetoRepository, times(1)).findById(1L);
    }

    @Test
    void getByIdShouldThrowNotFoundExceptionWhenProjetoDoesNotExist() {
        when(projetoRepository.findById(1L)).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            projetoService.getById(1L);
        });

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals("Projeto não encontrado!", exception.getReason());
        verify(projetoRepository, times(1)).findById(1L);
    }

    @Test
    void getAllShouldReturnListOfProjetoDTOWhenProjetosExist() {
        // Arrange
        Projeto projeto = new Projeto();
        projeto.setId(1L);
        projeto.setNome("Projeto Teste");

        when(projetoRepository.findAll()).thenReturn(List.of(projeto));

        List<ProjetoDTO> resultados = projetoService.getAll();

        assertEquals(1, resultados.size());
        assertEquals(1L, resultados.get(0).id());
        assertEquals("Projeto Teste", resultados.get(0).nome());
        verify(projetoRepository, times(1)).findAll();
    }

        @Test
    void getAllShouldReturnEmptyListWhenNoProjetosExist() {
        when(projetoRepository.findAll()).thenReturn(Collections.emptyList());

        List<ProjetoDTO> resultados = projetoService.getAll();

        assertTrue(resultados.isEmpty());
        verify(projetoRepository, times(1)).findAll();
    }
}
