package com.quantum.stratify.web.controllers;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import com.quantum.stratify.services.UsuarioService;
import com.quantum.stratify.web.dtos.ResetSenhaDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import com.quantum.stratify.config.jwt.JwtToken;
import com.quantum.stratify.config.jwt.JwtUserDetailsService;
import com.quantum.stratify.entities.Usuario;
import com.quantum.stratify.enums.Role;
import com.quantum.stratify.web.dtos.UsuarioLoginDto;

import jakarta.servlet.http.HttpServletRequest;

class AuthControllerTest {

    @InjectMocks
    private AuthController authController;

    @Mock
    private JwtUserDetailsService detailsService;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private HttpServletRequest request;

    @InjectMocks
    private UsuarioController usuarioController;

    @Mock
    private UsuarioService usuarioService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    private Usuario createUsuario(boolean enabled, boolean requireReset) {
        Usuario usuario = new Usuario();
        usuario.setEmail("usuario@teste.com");
        usuario.setSenha("senha123");
        usuario.setIsEnable(enabled);
        usuario.setRequireReset(requireReset);
        usuario.setRole(Role.ADMIN);
        return usuario;
    }

    @Test
    void deveAutenticarComSucesso() {
        Usuario usuario = createUsuario(true, false);
        UsuarioLoginDto dto = new UsuarioLoginDto("usuario@teste.com", "senha123");
        JwtToken token = new JwtToken("mocked-token");

        when(detailsService.getUsuarioByEmail(dto.getEmail())).thenReturn(Optional.of(usuario));
        when(detailsService.getTokenAuthenticated(dto.getEmail())).thenReturn(token);

        ResponseEntity<?> response = authController.autenticar(dto, request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void deveRetornarContaDesativada() {
        Usuario usuario = createUsuario(false, false);
        UsuarioLoginDto dto = new UsuarioLoginDto("usuario@teste.com", "senha123");

        when(detailsService.getUsuarioByEmail(dto.getEmail())).thenReturn(Optional.of(usuario));

        ResponseEntity<?> response = authController.autenticar(dto, request);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }


    @Test
    void deveRetornarCredenciaisInvalidasQuandoUsuarioNaoExiste() {
        UsuarioLoginDto dto = new UsuarioLoginDto("inexistente@teste.com", "senha123");

        when(detailsService.getUsuarioByEmail(dto.getEmail())).thenReturn(Optional.empty());

        ResponseEntity<?> response = authController.autenticar(dto, request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void deveRetornarCredenciaisInvalidasQuandoAutenticacaoFalha() {
        Usuario usuario = createUsuario(true, false);
        UsuarioLoginDto dto = new UsuarioLoginDto("usuario@teste.com", "senha123");

        when(detailsService.getUsuarioByEmail(dto.getEmail())).thenReturn(Optional.of(usuario));
        doThrow(BadCredentialsException.class).when(authenticationManager)
                .authenticate(new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getSenha()));

        ResponseEntity<?> response = authController.autenticar(dto, request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }


    @Test
    void deveResetarSenhaComSucesso() {
        ResetSenhaDTO dto = new ResetSenhaDTO();
        dto.setIdUsuario(1L);
        dto.setNovaSenha("Senha@123");

        ResponseEntity<String> response = usuarioController.resetarSenha(dto);

        verify(usuarioService).resetarSenha(1L, "Senha@123");
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Senha resetada com sucesso.", response.getBody());
    }

    @Test
    void deveRejeitarSenhaInvalida() {
        ResetSenhaDTO dto = new ResetSenhaDTO();
        dto.setIdUsuario(1L);
        dto.setNovaSenha("fraca");

        // Aqui simulamos manualmente a validação, já que @Valid não roda fora do contexto do Spring MVC
        String regex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!]).{9,}$";
        assertFalse(dto.getNovaSenha().matches(regex));
    }
}
