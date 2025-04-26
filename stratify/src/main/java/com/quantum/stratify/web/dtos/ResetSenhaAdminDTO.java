package com.quantum.stratify.web.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResetSenhaAdminDTO {
    @NotNull
    private Long idUsuario;
}
