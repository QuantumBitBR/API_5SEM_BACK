package com.quantum.stratify.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import com.quantum.stratify.entities.Usuario;
import com.quantum.stratify.repositories.UsuarioRepository;

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
}
