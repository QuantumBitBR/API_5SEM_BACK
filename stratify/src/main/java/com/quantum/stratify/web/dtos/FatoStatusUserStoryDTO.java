package com.quantum.stratify.web.dtos;

import com.quantum.stratify.entities.Status;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FatoStatusUserStoryDTO {
    private Status status;
    private Integer quantidadeUserStory;

    public FatoStatusUserStoryDTO(Status status, Integer quantidadeUserStory) {
        this.status = status;
        this.quantidadeUserStory = quantidadeUserStory;
    }
}
