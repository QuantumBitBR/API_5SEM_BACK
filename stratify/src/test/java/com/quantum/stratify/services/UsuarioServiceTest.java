package com.quantum.stratify.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import com.quantum.stratify.entities.Usuario;
import com.quantum.stratify.repositories.UsuarioRepository;
import com.quantum.stratify.web.dtos.AtribuirGestor;

@ExtendWith(MockitoExtension.class)
public class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioService usuarioService;

    @BeforeEach
    void setUp() {
    }

    @Test
    void shouldActivateUser() {
        // Arrange
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setIsEnable(false);

        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));

        // Act
        usuarioService.ativarUsuario(1L);

        // Assert
        assertTrue(usuario.getIsEnable());
        verify(usuarioRepository).save(usuario);
    }

    @Test
    void shouldDisableUser() {
        // Arrange
        Usuario usuario = new Usuario();
        usuario.setId(2L);
        usuario.setIsEnable(true);

        when(usuarioRepository.findById(2L)).thenReturn(Optional.of(usuario));

        // Act
        usuarioService.desativarUsuario(2L);

        // Assert
        assertFalse(usuario.getIsEnable());
        verify(usuarioRepository).save(usuario);
    }

    @Test
    void shouldThrowExceptionWhenUserNotFoundOnActivate() {
        when(usuarioRepository.findById(99L)).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            usuarioService.ativarUsuario(99L);
        });

        assertEquals("Usuário não encontrado", exception.getReason());
        verify(usuarioRepository, never()).save(any());
    }

    @Test
    void shouldThrowExceptionWhenUserNotFoundOnDisable() {
        when(usuarioRepository.findById(100L)).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            usuarioService.desativarUsuario(100L);
        });

        assertEquals("Usuário não encontrado", exception.getReason());
        verify(usuarioRepository, never()).save(any());
    }

    @Test
    void shouldAssignLideradosToGestorSuccessfully() {
        // Arrange
        Long idGestor = 10L;
        AtribuirGestor dto = new AtribuirGestor(idGestor, null);
        dto.setIdUsuarioGestor(idGestor);
        dto.setListaIdLiderados(Arrays.asList(1L, 2L));

        Usuario gestor = new Usuario();
        gestor.setId(idGestor);

        Usuario liderado1 = new Usuario();
        liderado1.setId(1L);

        Usuario liderado2 = new Usuario();
        liderado2.setId(2L);

        when(usuarioRepository.findById(idGestor)).thenReturn(Optional.of(gestor));
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(liderado1));
        when(usuarioRepository.findById(2L)).thenReturn(Optional.of(liderado2));

        // Act
        usuarioService.atribuirLideradosAoGestor(null, dto);

        // Assert
        assertEquals(gestor, liderado1.getGestor());
        assertEquals(gestor, liderado2.getGestor());

        verify(usuarioRepository, times(1)).save(liderado1);
        verify(usuarioRepository, times(1)).save(liderado2);
    }

    @Test
    void shouldThrowWhenGestorNotFound() {
        // Arrange
        AtribuirGestor dto = new AtribuirGestor(null, null);
        dto.setIdUsuarioGestor(99L);
        dto.setListaIdLiderados(Arrays.asList(1L));

        when(usuarioRepository.findById(99L)).thenReturn(Optional.empty());

        // Act + Assert
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () ->
            usuarioService.atribuirLideradosAoGestor(null, dto)
        );

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals("Gestor não encontrado", exception.getReason());
    }

    @Test
    void shouldThrowWhenLideradoNotFound() {
        // Arrange
        Long idGestor = 10L;
        AtribuirGestor dto = new AtribuirGestor(idGestor, null);
        dto.setIdUsuarioGestor(idGestor);
        dto.setListaIdLiderados(Arrays.asList(1L, 2L));

        Usuario gestor = new Usuario();
        gestor.setId(idGestor);

        when(usuarioRepository.findById(idGestor)).thenReturn(Optional.of(gestor));
        when(usuarioRepository.findById(1L)).thenReturn(Optional.empty());

        // Act + Assert
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () ->
            usuarioService.atribuirLideradosAoGestor(null, dto)
        );

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals("Usuário liderado não encontrado: 1", exception.getReason());
    }
}
