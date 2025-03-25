package com.quantum.stratify.web.dtos;

import org.springframework.http.ResponseEntity;

import com.quantum.stratify.entities.Projeto;
import com.quantum.stratify.entities.Tag;

public record FatoTagUserStoryDTO(Long id, Tag tag, Projeto projeto, Long quantidadeUserStories) {

}
