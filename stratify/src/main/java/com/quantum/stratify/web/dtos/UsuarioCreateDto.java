package com.quantum.stratify.web.dtos;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UsuarioCreateDto {

    @NotBlank
    @Email(message = "Formato do e-mail inválido.")
    private String email;
    @NotBlank
    private String senha;
}
