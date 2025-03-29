package com.quantum.stratify;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.quantum.stratify.entities.FatoUserStoryTemporais;
import com.quantum.stratify.entities.Periodo;
import com.quantum.stratify.entities.Projeto;
import com.quantum.stratify.repositories.FatoUserStoryTemporaisRepository;
import com.quantum.stratify.repositories.PeriodoRepository;
import com.quantum.stratify.services.FatoUserStoryTemporaisService;
import com.quantum.stratify.services.ProjetoService;
import com.quantum.stratify.web.dtos.ResponseQuantidadeCardsByPeriodo;

@ExtendWith(MockitoExtension.class)
public class FatoUserStoryTemporaisServiceTest {

    @Mock
    private FatoUserStoryTemporaisRepository fatoRepository;

    @Mock
    private PeriodoRepository periodoRepository;

    @Mock
    private ProjetoService projetoService;

    @InjectMocks
    private FatoUserStoryTemporaisService fatoService;

    @Test
    void whenGetAllUserStories_thenReturnAllMetrics() {
        // Mock data
        Periodo periodo = new Periodo();
        periodo.setId(1L);
        periodo.setNome("Q1 2023");

        FatoUserStoryTemporais fato1 = new FatoUserStoryTemporais();
        fato1.setPeriodo(periodo);
        fato1.setQuantidadeUserStoriesCriadas(10);
        fato1.setQuantidadeUserStoriesFinalizadas(8);

        FatoUserStoryTemporais fato2 = new FatoUserStoryTemporais();
        fato2.setPeriodo(periodo);
        fato2.setQuantidadeUserStoriesCriadas(15);
        fato2.setQuantidadeUserStoriesFinalizadas(12);

        // Mock repository
        when(fatoRepository.findAll()).thenReturn(Arrays.asList(fato1, fato2));

        // Test
        List<ResponseQuantidadeCardsByPeriodo> result = fatoService.getUserStoriesByPeriodo(null, null);

        // Print result for debugging
        System.out.println("=== Resultado ===");
        result.forEach(r -> System.out.println(r));

        // Assertions
        assertEquals(2, result.size());
        assertEquals("Q1 2023", result.get(0).periodo());
        assertEquals(10, result.get(0).quantidadeCriadas());
        assertEquals(8, result.get(0).quantidadeFinalizadas());
    }

    @Test
    void whenGetByPeriodo_thenReturnFilteredMetrics() {
        // Mock data
        Long periodoId = 1L;
        Periodo periodo = new Periodo();
        periodo.setId(periodoId);
        periodo.setNome("Q2 2023");

        FatoUserStoryTemporais fato = new FatoUserStoryTemporais();
        fato.setPeriodo(periodo);
        fato.setQuantidadeUserStoriesCriadas(20);
        fato.setQuantidadeUserStoriesFinalizadas(15);

        // Mock repositories
        when(periodoRepository.findById(periodoId)).thenReturn(Optional.of(periodo));
        when(fatoRepository.findByPeriodo(periodo)).thenReturn(Arrays.asList(fato));

        // Test
        List<ResponseQuantidadeCardsByPeriodo> result = fatoService.getUserStoriesByPeriodo(null, periodoId);

        // Print result for debugging
        System.out.println("=== Resultado ===");
        result.forEach(r -> System.out.println(r));

        // Assertions
        assertEquals(1, result.size());
        assertEquals("Q2 2023", result.get(0).periodo());
        assertEquals(20, result.get(0).quantidadeCriadas());
        assertEquals(15, result.get(0).quantidadeFinalizadas());
    }

    @Test
    void whenGetByProjetoAndPeriodo_thenReturnFilteredMetrics() {
        // Mock data
        Long projetoId = 1L;
        Long periodoId = 2L;
        
        Projeto projeto = new Projeto();
        projeto.setId(projetoId);
        
        Periodo periodo = new Periodo();
        periodo.setId(periodoId);
        periodo.setNome("Q3 2023");

        FatoUserStoryTemporais fato = new FatoUserStoryTemporais();
        fato.setProjeto(projeto);
        fato.setPeriodo(periodo);
        fato.setQuantidadeUserStoriesCriadas(30);
        fato.setQuantidadeUserStoriesFinalizadas(25);

        // Mock repositories and services
        when(projetoService.getById(projetoId)).thenReturn(projeto);
        when(periodoRepository.findById(periodoId)).thenReturn(Optional.of(periodo));
        when(fatoRepository.findByProjetoAndPeriodo(projeto, periodo)).thenReturn(Arrays.asList(fato));

        // Test
        List<ResponseQuantidadeCardsByPeriodo> result = fatoService.getUserStoriesByPeriodo(projetoId, periodoId);

        // Print result for debugging
        System.out.println("=== Resultado ===");
        result.forEach(r -> System.out.println(r));

        // Assertions
        assertEquals(1, result.size());
        assertEquals("Q3 2023", result.get(0).periodo());
        assertEquals(30, result.get(0).quantidadeCriadas());
        assertEquals(25, result.get(0).quantidadeFinalizadas());
    }

    @Test
    void whenPeriodoNotFound_thenThrowException() {
        Long periodoId = 99L;
        when(periodoRepository.findById(periodoId)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            fatoService.getUserStoriesByPeriodo(null, periodoId);
        });
    }

    @Test
    void whenNoDataFound_thenReturnEmptyList() {
        when(fatoRepository.findAll()).thenReturn(Arrays.asList());

        List<ResponseQuantidadeCardsByPeriodo> result = fatoService.getUserStoriesByPeriodo(null, null);

        assertTrue(result.isEmpty());
    }
}