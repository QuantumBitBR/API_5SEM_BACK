package com.quantum.stratify.web.dtos;

public class QuantidadeCardsPorSprintDTO {
    private String sprint;
    private Long quantidade;

    public QuantidadeCardsPorSprintDTO(String sprint, Long quantidade) {
        this.sprint = sprint;
        this.quantidade = quantidade;
    }

    // Getters e Setters
    public String getSprint() {
        return sprint;
    }

    public void setSprint(String sprint) {
        this.sprint = sprint;
    }

    public Long getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Long quantidade) {
        this.quantidade = quantidade;
    }
}