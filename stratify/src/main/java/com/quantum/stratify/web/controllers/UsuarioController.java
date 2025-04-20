package com.quantum.stratify.web.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.quantum.stratify.services.UsuarioService;
import com.quantum.stratify.web.dtos.AtribuirGestor;
import com.quantum.stratify.web.dtos.UsuarioPorRoleDTO;
import com.quantum.stratify.enums.Role;

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

    @PostMapping("/lideradosgestor")
    @Operation(summary = "Atribui usuários liderados a um gestor")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Usuários atribuídos com sucesso"),
        @ApiResponse(responseCode = "404", description = "Gestor ou algum usuário não encontrado")
    })
    public ResponseEntity<Void> atribuirLiderados(
        @RequestBody AtribuirGestor dto
    ) {
    usuarioService.atribuirLideradosAoGestor(dto);
    return ResponseEntity.ok().build();
    }
    
    @GetMapping("/por-role/{role}")
    @Operation(summary = "Listar usuários por role", 
          description = "Filtra usuários pelo tipo de perfil (USER, GESTOR, OPERADOR, ADMIN)")
    @ApiResponses({
    @ApiResponse(responseCode = "200", description = "Lista de usuários retornada"),
    @ApiResponse(responseCode = "404", description = "Nenhum usuário encontrado para esta role")
      })
    public ResponseEntity<List<UsuarioPorRoleDTO>> listarPorRole(
        @PathVariable Role role) {
    return ResponseEntity.ok(usuarioService.listarPorRole(role));
    }
    
}
