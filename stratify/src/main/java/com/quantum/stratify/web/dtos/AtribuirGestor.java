package com.quantum.stratify.web.dtos;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AtribuirGestor {
    private Long idUsuarioGestor;
    private List<Long> listaIdLiderados;

    public AtribuirGestor(Long idUsuarioGestor, List<Long> listaIdLiderados){
        this.idUsuarioGestor = idUsuarioGestor;
        this.listaIdLiderados = listaIdLiderados;
    }

}
