package com.quantum.stratify.web.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioResponseDto {
    private Long id;
    private String role;
    private String token;
    private boolean requireReset;
    private boolean enabled;

    public UsuarioResponseDto(Long id,String token, boolean requireReset,boolean enabled, String role) {
        this.id = id;
        this.token = token;
        this.requireReset = requireReset;
        this.enabled = enabled;
        this.role = role;
    }

}
