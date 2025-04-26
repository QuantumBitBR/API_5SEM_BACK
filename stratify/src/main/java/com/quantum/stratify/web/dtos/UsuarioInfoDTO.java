package com.quantum.stratify.web.dtos;

import com.quantum.stratify.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioInfoDTO {
    private Long id;
    private String email;
    private Long gestorId;
    private Role role;
    private boolean requireReset;
    private Boolean isEnable;
}

