package com.quantum.stratify.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.quantum.stratify.repositories.UserStoryRepository;
import com.quantum.stratify.web.dtos.QuantidadeCardsPorTagDTO;

@Service
public class UserStoryTagService {

    private final UserStoryRepository userStoryRepository;

    @Autowired
    public UserStoryTagService(UserStoryRepository userStoryRepository) {
        this.userStoryRepository = userStoryRepository;
    }

    public List<QuantidadeCardsPorTagDTO> getQuantidadeUserStoriesByTag(Long projetoId, Long usuarioId) {
        return userStoryRepository.contarUserStoriesPorTag(projetoId, usuarioId);
    }
}

    