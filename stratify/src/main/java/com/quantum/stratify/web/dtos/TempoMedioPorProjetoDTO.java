package com.quantum.stratify.web.dtos;

public record TempoMedioPorProjetoDTO(Long idUserStory, String descricao, Long tempoMedio) {
    public TempoMedioPorProjetoDTO(Long idUserStory, String descricao, Double tempoMedio) {
        this(idUserStory, descricao, tempoMedio != null ? tempoMedio.longValue() : 0L);
    }
}
