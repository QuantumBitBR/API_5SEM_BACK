package com.quantum.stratify.web.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PercentualStatusUsuarioDTO {
    private String nomeStatus;
    private Double percentual;

    public PercentualStatusUsuarioDTO(String nomeStatus, Double percentual){
        this.nomeStatus = nomeStatus;
        this.percentual = percentual; 
    }
}
