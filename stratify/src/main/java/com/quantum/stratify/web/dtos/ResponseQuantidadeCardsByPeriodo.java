package com.quantum.stratify.web.dtos;

import java.time.LocalDate;

public record ResponseQuantidadeCardsByPeriodo(Long quantidadeCriadas, Long quantidadeFinalizadas, LocalDate dataInicio, LocalDate dataFim
) {
    public ResponseQuantidadeCardsByPeriodo {
        if (quantidadeCriadas == null) {
            quantidadeCriadas = 0L;
        }
        if (quantidadeFinalizadas == null) {
            quantidadeFinalizadas = 0L;
        }
    }
}