package com.quantum.stratify.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
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
import com.quantum.stratify.enums.Role;
import com.quantum.stratify.repositories.UsuarioRepository;
import com.quantum.stratify.web.dtos.AtribuirGestor;
import com.quantum.stratify.web.dtos.UsuarioDTO;
import com.quantum.stratify.web.dtos.UsuarioPorRoleDTO;
import com.quantum.stratify.enums.Role;


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
        usuarioService.atribuirLideradosAoGestor(dto);

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
            usuarioService.atribuirLideradosAoGestor(dto)
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
            usuarioService.atribuirLideradosAoGestor(dto)
        );

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals("Usuário liderado não encontrado: 1", exception.getReason());}
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

    @Test
    void listarPorRole_deveRetornarUsuariosQuandoExistirem() {
        // Arrange
        Role role = Role.GESTOR;
        List<UsuarioPorRoleDTO> usuariosMock = Arrays.asList(
            new UsuarioPorRoleDTO(1L, "João", "joao@email.com", role, true, null),
            new UsuarioPorRoleDTO(2L, "Maria", "maria@email.com", role, true, "João")
        );
        
        when(usuarioRepository.findByRole(role)).thenReturn(usuariosMock);

        // Act
        List<UsuarioPorRoleDTO> resultado = usuarioService.listarPorRole(role);

        // Assert
        assertEquals(2, resultado.size());
        assertEquals("João", resultado.get(0).getNome());
        assertEquals("Maria", resultado.get(1).getNome());
        assertEquals("joao@email.com", resultado.get(0).getEmail());
        verify(usuarioRepository).findByRole(role);
    }

    @Test
    void listarPorRole_deveLancarExcecaoQuandoNenhumUsuarioEncontrado() {
        // Arrange
        Role role = Role.OPERADOR;
        when(usuarioRepository.findByRole(role)).thenReturn(Collections.emptyList());

        // Act 
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () ->
            usuarioService.listarPorRole(role)
        );
        //  Assert
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals("Nenhum usuário encontrado com a role: OPERADOR", exception.getReason());
        verify(usuarioRepository).findByRole(role);
    }

    @Test
    void listarPorRole_deveRetornarUsuariosComGestorQuandoExistir() {
        // Arrange
        Role role = Role.USER;
        List<UsuarioPorRoleDTO> usuariosMock = List.of(
            new UsuarioPorRoleDTO(3L, "Pedro", "pedro@email.com", role, true, "Carlos")
        );
        
        when(usuarioRepository.findByRole(role)).thenReturn(usuariosMock);

        // Act
        List<UsuarioPorRoleDTO> resultado = usuarioService.listarPorRole(role);

        // Assert
        assertEquals(1, resultado.size());
        assertEquals("Pedro", resultado.get(0).getNome());
        assertEquals("Carlos", resultado.get(0).getGestorNome());
        verify(usuarioRepository).findByRole(role);
    }

    @Test
    void listarPorRole_deveRetornarUsuariosInativosQuandoExistirem() {
        // Arrange
        Role role = Role.ADMIN;
        List<UsuarioPorRoleDTO> usuariosMock = List.of(
            new UsuarioPorRoleDTO(4L, "Admin", "admin@email.com", role, false, null)
        );
        
        when(usuarioRepository.findByRole(role)).thenReturn(usuariosMock);

        // Act
        List<UsuarioPorRoleDTO> resultado = usuarioService.listarPorRole(role);

        // Assert
        assertEquals(1, resultado.size());
        assertFalse(resultado.get(0).getIsEnable());
        verify(usuarioRepository).findByRole(role); 
    }
    @Test
    void shouldUpdateUserRoleToUSER() {
        shouldUpdateUserRoleSuccessfully(Role.USER);
    }

    @Test
    void shouldUpdateUserRoleToADMIN() {
        shouldUpdateUserRoleSuccessfully(Role.ADMIN);
    }

    @Test
    void shouldUpdateUserRoleToOPERADOR() {
        shouldUpdateUserRoleSuccessfully(Role.OPERADOR);
    }

    @Test
    void shouldUpdateUserRoleToGESTOR() {
        shouldUpdateUserRoleSuccessfully(Role.GESTOR);
    }

    private void shouldUpdateUserRoleSuccessfully(Role novaRole) {
        // Arrange
        Long userId = 1L;

        Usuario usuario = new Usuario();
        usuario.setId(userId);
        usuario.setNome("Teste");
        usuario.setRole(Role.USER);

        when(usuarioRepository.findById(userId)).thenReturn(Optional.of(usuario));
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);

        // Act
        Usuario atualizado = usuarioService.alterarRole(userId, novaRole);

        // Assert
        assertEquals(novaRole, atualizado.getRole());
        verify(usuarioRepository).save(usuario);
    }

    @Test
    void shouldThrowExceptionWhenUserNotFoundToUpdateRole() {
    // Arrange
    Long userId = 99L;
    Role novaRole = Role.ADMIN;

    when(usuarioRepository.findById(userId)).thenReturn(Optional.empty());

    // Act + Assert
    ResponseStatusException exception = assertThrows(ResponseStatusException.class, () ->
        usuarioService.alterarRole(userId, novaRole)
    );

    assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
    assertEquals("Usuário não encontrado", exception.getReason());
    verify(usuarioRepository, never()).save(any());

    }
}
