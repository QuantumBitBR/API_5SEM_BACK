package com.quantum.stratify.web.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuantidadeCardsPorStatusDTO {

    private String nomeStatus;
    private Long quantidade;

    public QuantidadeCardsPorStatusDTO(Long idStatus, String nomeStatus, Long quantidade) {
        this.nomeStatus = nomeStatus;
        this.quantidade = quantidade;
    }
}
