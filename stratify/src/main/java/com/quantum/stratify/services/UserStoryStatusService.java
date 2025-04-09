package com.quantum.stratify.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.quantum.stratify.repositories.UserStoryRepository;
import com.quantum.stratify.web.dtos.PercentualStatusUsuarioDTO;
import com.quantum.stratify.web.dtos.QuantidadeCardsPorStatusDTO;

@Service
public class UserStoryStatusService {

    private final UserStoryRepository userStoryRepository;

    public UserStoryStatusService(UserStoryRepository userStoryRepository) {
        this.userStoryRepository = userStoryRepository;
    }

    public List<PercentualStatusUsuarioDTO> getPercentualUserStoriesPorStatus(Long projetoId, Long usuarioId) {
        List<QuantidadeCardsPorStatusDTO> dados = userStoryRepository.contarUserStoriesPorStatus(projetoId, usuarioId);

        long total = dados.stream().mapToLong(QuantidadeCardsPorStatusDTO::getQuantidade).sum();

        return dados.stream()
            .map(item -> new PercentualStatusUsuarioDTO(
                item.getNomeStatus(),
                total == 0 ? 0.0 : (item.getQuantidade() * 100.0) / total
            ))
            .collect(Collectors.toList());
    }
}
