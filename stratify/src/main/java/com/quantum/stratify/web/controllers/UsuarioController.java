package com.quantum.stratify.web.controllers;

import java.util.List;

import com.quantum.stratify.web.dtos.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.quantum.stratify.entities.Usuario;
import com.quantum.stratify.services.UsuarioService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

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

    @PutMapping("/{idUsuario}")
    @Operation(summary = "Alterar Role de um usuário", description = "Atualiza a role do usuário")
    @ApiResponses({
    @ApiResponse(responseCode = "200", description = "Role alterada com sucesso"),
    @ApiResponse(responseCode = "400", description = "Dados inválidos"),
    @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    public ResponseEntity<UsuarioDTO> alterarRoleUsuario(
        @PathVariable Long idUsuario,
        @Valid @RequestBody AlterarRoleDTO dto) {

    Usuario atualizado = usuarioService.alterarRole(idUsuario, dto.getRole());
    return ResponseEntity.ok(new UsuarioDTO(atualizado.getId(), atualizado.getNome()));
    }

    @GetMapping("filtrarprojetogestor")
    @Operation(summary = "Buscar usuários por projeto e/ou gestor")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Usuários encontrados com sucesso")
    })
    public ResponseEntity<List<UsuarioDTO>> filtrarUsuarios(
        @RequestParam(required = false) Long idProjeto,
        @RequestParam(required = false) Long idGestor
    ) {
        List<UsuarioDTO> usuarios = usuarioService.buscarUsuariosPorProjetoEGestor(idProjeto, idGestor);
        return ResponseEntity.ok(usuarios);
    }

    @PutMapping("/resetar-senha")
    @Operation(summary = "Resetar senha de um usuário")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Senha resetada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Senha inválida"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    public ResponseEntity<String> resetarSenha(@Valid @RequestBody ResetSenhaDTO dto) {
        usuarioService.resetarSenha(dto.getIdUsuario(), dto.getNovaSenha());
        return ResponseEntity.ok( "Senha resetada com sucesso. id:" + dto.getIdUsuario());
    }
    @GetMapping("/listar")
    @Operation(summary = "Listar usuários (info reduzida)")
    @ApiResponse(responseCode = "200", description = "Lista de usuários retornada com sucesso")
    public ResponseEntity<List<UsuarioInfoDTO>> listarUsuariosInfo() {
        return ResponseEntity.ok(usuarioService.listarUsuariosInfo());
    }

    @PostMapping("/set-require-reset")
    @Operation(summary = "Set require reset de um usuário")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Require reset atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    public ResponseEntity<Void> setRequireReset(@RequestBody UsuarioResponseDto dto) {
        usuarioService.setRequireReset(dto.getId(), dto.isRequireReset());
        return ResponseEntity.ok().build();
    }

}
