package com.quantum.stratify.web.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResetSenhaDTO {

    private Long idUsuario;

    @NotBlank
    @Pattern(
            regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!]).{9,}$",
            message = "A senha deve ter mais de 8 caracteres e conter número, letra maiúscula, minúscula e caractere especial."
    )
    private String novaSenha;
}
