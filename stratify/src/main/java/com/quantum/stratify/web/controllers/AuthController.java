package com.quantum.stratify.web.controllers;
import com.quantum.stratify.config.jwt.JwtToken;
import com.quantum.stratify.config.jwt.JwtUserDetailsService;
import com.quantum.stratify.entities.Usuario;
import com.quantum.stratify.web.dtos.UsuarioLoginDto;
import com.quantum.stratify.web.dtos.UsuarioResponseDto;
import com.quantum.stratify.web.exceptions.ErrorMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/")
@Tag(name = "Autenticação", description = "Recurso para proceder com a autenticação na API.")
@CrossOrigin("*")
public class AuthController {

    private final JwtUserDetailsService detailsService;
    private final AuthenticationManager authenticationManager;


    @Operation(
            summary = "Autenticar na API", description = "Recurso de autenticação na API"
    )
    @PostMapping("/auth")
    public ResponseEntity<?> autenticar(@RequestBody @Valid UsuarioLoginDto dto, HttpServletRequest request) {
        log.info("Processo de autenticação pelo login {}", dto.getEmail());

        try {
            // Primeiro, recuperar o usuario
            Optional<Usuario> optionalUsuario = detailsService.getUsuarioByEmail(dto.getEmail());

            if (optionalUsuario.isEmpty()) {
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body(new ErrorMessage(request, HttpStatus.BAD_REQUEST, "Credenciais inválidas"));
            }

            Usuario usuario = optionalUsuario.get();

            // Verificar se a conta está ativa
            if (!usuario.isEnabled()) {
                return ResponseEntity
                        .status(HttpStatus.UNAUTHORIZED)
                        .body(new ErrorMessage(request, HttpStatus.UNAUTHORIZED, "Conta desativada. Contate o administrador."));
            }

            // Realiza a autenticação
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getSenha());

            authenticationManager.authenticate(authenticationToken);

            JwtToken token = detailsService.getTokenAuthenticated(dto.getEmail());

            return ResponseEntity.ok(new UsuarioResponseDto(usuario.getId(), token.getToken(), usuario.isRequireReset(), usuario.isEnabled() ,usuario.getRole().name()));

        } catch (AuthenticationException ex) {
            log.warn("Credenciais inválidas para o usuário '{}'", dto.getEmail());
            return ResponseEntity
                    .badRequest()
                    .body(new ErrorMessage(request, HttpStatus.BAD_REQUEST, "Credenciais Inválidas"));
        }
    }

}

