package com.quantum.stratify.web.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UsuarioSenhaDto {
    @NotBlank
    private String senhaAtual;
    @NotBlank
    private String novaSenha;
    @NotBlank
    private String confirmaSenha;
}

