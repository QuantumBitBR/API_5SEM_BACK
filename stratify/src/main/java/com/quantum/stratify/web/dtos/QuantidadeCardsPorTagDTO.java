package com.quantum.stratify.web.dtos;

public class QuantidadeCardsPorTagDTO {
    private Long idTag;
    private String nomeTag;
    private Long idProjeto;
    private Long quantidade;

    public QuantidadeCardsPorTagDTO(Long idTag, String nomeTag, Long idProjeto, Long quantidade) {
        this.idTag = idTag;
        this.nomeTag = nomeTag;
        this.idProjeto = idProjeto;
        this.quantidade = quantidade;
    }

    // Getters e setters
    public Long getIdTag() {
        return idTag;
    }

    public void setIdTag(Long idTag) {
        this.idTag = idTag;
    }

    public String getNomeTag() {
        return nomeTag;
    }

    public void setNomeTag(String nomeTag) {
        this.nomeTag = nomeTag;
    }

    public Long getIdProjeto() {
        return idProjeto;
    }

    public void setIdProjeto(Long idProjeto) {
        this.idProjeto = idProjeto;
    }

    public Long getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Long quantidade) {
        this.quantidade = quantidade;
    }
}
