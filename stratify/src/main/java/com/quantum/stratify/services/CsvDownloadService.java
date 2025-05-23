package com.quantum.stratify.services;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;

import com.opencsv.CSVWriter;
import com.quantum.stratify.web.dtos.PercentualStatusUsuarioDTO;
import com.quantum.stratify.web.dtos.QuantidadeCardsPorSprintDTO;
import com.quantum.stratify.web.dtos.QuantidadeCardsPorTagDTO;
import com.quantum.stratify.web.dtos.ResponseQuantidadeCardsByPeriodo;
import com.quantum.stratify.web.dtos.TempoMedioPorProjetoDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class CsvDownloadService {

    @Autowired
    private UserStoryService userStoryService;
    
    @Autowired
    private FatoUserStoryTemporaisService fatoUserStoryTemporaisService;

    @Autowired
    private UserStoryTagService userStoryTagService;

    @Autowired
    private UserStoryStatusService userStoryStatusService;

    @Autowired
    private FatoEficienciaUserStoryService fatoEficienciaUserStoryService;
    public InputStreamResource generateCsv(Long projectId, Long userId, String exportType) {
        List<String[]> csvData;

    switch (exportType.toLowerCase()) {
    case "cardsporsprint": {
        List<QuantidadeCardsPorSprintDTO> lista = userStoryService.getQuantidadeUserStoriesBySprint(projectId, userId);
        csvData = new ArrayList<>();
        csvData.add(new String[]{"Sprint", "Quantidade"});
        for (QuantidadeCardsPorSprintDTO dto : lista) {
            csvData.add(new String[]{dto.getSprint(), String.valueOf(dto.getQuantidade())});
        }
        log.info("ExportType=cardsporsprint → Encontrados {} registros para projeto={} usuário={}", lista.size(), projectId, userId);
        break;
    }
    case "cardsporperiodo": {
        List<ResponseQuantidadeCardsByPeriodo> lista = fatoUserStoryTemporaisService.getUserStoriesByPeriodoAndUser(projectId, userId);
        csvData = new ArrayList<>();
        csvData.add(new String[]{"Período", "Criadas", "Finalizadas"});
        for (ResponseQuantidadeCardsByPeriodo dto : lista) {
            csvData.add(new String[]{
                dto.periodo(),
                String.valueOf(dto.quantidadeCriadas()),
                String.valueOf(dto.quantidadeFinalizadas())
            });
        }
        break;
    }
    case "cardsporetiqueta": {
        List<QuantidadeCardsPorTagDTO> lista = userStoryTagService.getQuantidadeUserStoriesByTag(projectId, userId);
        csvData = new ArrayList<>();
        csvData.add(new String[]{"Tag", "Quantidade"});
        for (QuantidadeCardsPorTagDTO dto : lista) {
            csvData.add(new String[]{dto.getNomeTag(), String.valueOf(dto.getQuantidade())});
        }
        break;
    }
    case "cardsporstatus": {
        List<PercentualStatusUsuarioDTO> lista = userStoryStatusService.getPercentualUserStoriesPorStatus(projectId, userId);
        csvData = new ArrayList<>();
        csvData.add(new String[]{"Status", "Percentual"});
        for (PercentualStatusUsuarioDTO dto : lista) {
            csvData.add(new String[]{dto.getNomeStatus(), String.valueOf(dto.getPercentual())});
        }
        break;
    }
    case "tempomedio": {
        List<TempoMedioPorProjetoDTO> lista = fatoEficienciaUserStoryService.getTempoMedioFiltrado(projectId, userId);
        csvData = new ArrayList<>();
        csvData.add(new String[]{"ID User Story", "Descrição", "Tempo Médio"});
        for (TempoMedioPorProjetoDTO dto : lista) {
            csvData.add(new String[]{
                String.valueOf(dto.idUserStory()),
                dto.descricao(),
                String.valueOf(dto.tempoMedio())
            });
        }
        break;
    }
    default:
        throw new IllegalArgumentException("Tipo de exportação inválido: " + exportType);
    }

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try (CSVWriter writer = new CSVWriter(new OutputStreamWriter(out))) {
            for (String[] line : csvData) {
                writer.writeNext(line);
            }
        } catch (IOException e) {
            throw new RuntimeException("Erro ao gerar CSV", e);
        }

        return new InputStreamResource(new ByteArrayInputStream(out.toByteArray()));
    }
}
