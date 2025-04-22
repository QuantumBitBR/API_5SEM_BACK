package com.quantum.stratify.web.dtos;
import com.quantum.stratify.enums.Role;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AlterarRoleDTO {
    @NotNull(message = "Role não pode ser nula")
    private Role role;
}