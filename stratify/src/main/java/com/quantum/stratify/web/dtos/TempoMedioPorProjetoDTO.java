package com.quantum.stratify.web.dtos;

public record TempoMedioPorProjetoDTO(Long idUserStory, String descricao, Long tempoMedio, Long quantidadeRetrabalhos) {
    public TempoMedioPorProjetoDTO(Long idUserStory, String descricao, Double tempoMedio, Long quantidadeRetrabalhos) {
        this(idUserStory, descricao, tempoMedio != null ? tempoMedio.longValue() : 0L, quantidadeRetrabalhos);
    }
}
