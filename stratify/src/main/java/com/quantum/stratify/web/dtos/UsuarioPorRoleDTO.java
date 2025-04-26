package com.quantum.stratify.web.dtos;



import com.quantum.stratify.enums.Role;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioPorRoleDTO {
    private Long id;
    private String nome;
    private String email;
    private Role role;
    private Boolean isEnable;
    private String gestorNome;

    public UsuarioPorRoleDTO(Long id, String nome, String email, Role role, Boolean isEnable, String gestorNome) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.role = role;
        this.isEnable = isEnable;
        this.gestorNome = gestorNome;
    }

    
}