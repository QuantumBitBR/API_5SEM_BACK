package com.quantum.stratify.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.quantum.stratify.services.UsuarioService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@CrossOrigin("*")
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    public UsuarioService usuarioService;

    @PutMapping("/{id}/ativar")
    @Operation(summary = "Ativar usuário", description = "Ativa um usuário com base no ID")
    @ApiResponses({
    @ApiResponse(responseCode = "204", description = "Usuário ativado com sucesso"),
    @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    public ResponseEntity<Void> ativarUsuario(@PathVariable Long id) {
    usuarioService.ativarUsuario(id);
    return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/desativar")
    @Operation(summary = "Desativar usuário", description = "Desativa um usuário com base no ID")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Usuário desativado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    public ResponseEntity<Void> desativarUsuario(@PathVariable Long id) {
    usuarioService.desativarUsuario(id);
    return ResponseEntity.noContent().build();
    }
    
}
