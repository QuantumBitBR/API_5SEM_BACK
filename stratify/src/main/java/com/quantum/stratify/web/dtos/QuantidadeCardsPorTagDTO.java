package com.quantum.stratify.web.dtos;

public class QuantidadeCardsPorTagDTO {
    private String nomeTag;
    private Long quantidade;

    public QuantidadeCardsPorTagDTO(String nomeTag, Long quantidade) {
        this.nomeTag = nomeTag;
        this.quantidade = quantidade;
    }

    // Getters e setters
    public String getNomeTag() {
        return nomeTag;
    }

    public void setNomeTag(String nomeTag) {
        this.nomeTag = nomeTag;
    }

    public Long getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Long quantidade) {
        this.quantidade = quantidade;
    }
}
