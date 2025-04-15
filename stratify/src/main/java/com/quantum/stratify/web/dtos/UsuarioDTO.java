package com.quantum.stratify.web.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioDTO {
    
    private Long idUsuario;
    private String nomeUsuario;

    public UsuarioDTO(Long idUsuario, String nomeUsuario) {
        this.idUsuario = idUsuario;
        this.nomeUsuario = nomeUsuario;
    }

}
