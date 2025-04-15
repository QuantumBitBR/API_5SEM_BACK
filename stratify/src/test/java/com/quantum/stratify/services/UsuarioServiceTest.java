package com.quantum.stratify.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.List;
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
import com.quantum.stratify.web.dtos.UsuarioDTO;

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
    void deveRetornarTodosUsuariosQuandoFiltrosForemNulos() {
        // Arrange
        List<UsuarioDTO> usuariosMock = List.of(
            new UsuarioDTO(1L, "João"),
            new UsuarioDTO(2L, "Maria")
        );
        when(usuarioRepository.findUsuarioByProjetoAndGestor(null, null)).thenReturn(usuariosMock);

        // Act
        List<UsuarioDTO> resultado = usuarioService.buscarUsuariosPorProjetoEGestor(null, null);

        // Assert
        assertEquals(2, resultado.size());
        assertEquals("João", resultado.get(0).getNomeUsuario());
        verify(usuarioRepository).findUsuarioByProjetoAndGestor(null, null);
    }

    @Test
    void deveRetornarUsuariosDoProjetoQuandoSomenteIdProjetoForInformado() {
        // Arrange
        Long idProjeto = 10L;
        List<UsuarioDTO> usuariosMock = List.of(new UsuarioDTO(3L, "Pedro"));
        when(usuarioRepository.findUsuarioByProjetoAndGestor(idProjeto, null)).thenReturn(usuariosMock);

        // Act
        List<UsuarioDTO> resultado = usuarioService.buscarUsuariosPorProjetoEGestor(idProjeto, null);

        // Assert
        assertEquals(1, resultado.size());
        assertEquals("Pedro", resultado.get(0).getNomeUsuario());
        verify(usuarioRepository).findUsuarioByProjetoAndGestor(idProjeto, null);
    }

    @Test
    void deveRetornarUsuariosDoGestorQuandoSomenteIdGestorForInformado() {
        // Arrange
        Long idGestor = 20L;
        List<UsuarioDTO> usuariosMock = List.of(new UsuarioDTO(4L, "Lucia"));
        when(usuarioRepository.findUsuarioByProjetoAndGestor(null, idGestor)).thenReturn(usuariosMock);

        // Act
        List<UsuarioDTO> resultado = usuarioService.buscarUsuariosPorProjetoEGestor(null, idGestor);

        // Assert
        assertEquals(1, resultado.size());
        assertEquals("Lucia", resultado.get(0).getNomeUsuario());
        verify(usuarioRepository).findUsuarioByProjetoAndGestor(null, idGestor);
    }

    @Test
    void deveRetornarUsuariosFiltradosPorProjetoEGestorQuandoAmbosInformados() {
        // Arrange
        Long idProjeto = 10L;
        Long idGestor = 20L;
        List<UsuarioDTO> usuariosMock = List.of(new UsuarioDTO(5L, "Carlos"));
        when(usuarioRepository.findUsuarioByProjetoAndGestor(idProjeto, idGestor)).thenReturn(usuariosMock);

        // Act
        List<UsuarioDTO> resultado = usuarioService.buscarUsuariosPorProjetoEGestor(idProjeto, idGestor);

        // Assert
        assertEquals(1, resultado.size());
        assertEquals("Carlos", resultado.get(0).getNomeUsuario());
        verify(usuarioRepository).findUsuarioByProjetoAndGestor(idProjeto, idGestor);
    }
}
