package com.quantum.stratify.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.quantum.stratify.entities.FatoUserStoryTemporais;
import com.quantum.stratify.entities.Projeto;
import com.quantum.stratify.entities.Usuario;
import com.quantum.stratify.repositories.FatoUserStoryTemporaisRepository;
import com.quantum.stratify.web.dtos.ResponseQuantidadeCardsByPeriodo;


@Service
public class FatoUserStoryTemporaisService {

    @Autowired
    private FatoUserStoryTemporaisRepository fatoUserStoryTemporaisRepository;

    @Autowired
    private ProjetoService projetoService;

    @Autowired
    private UsuarioService usuarioService;

    public List<ResponseQuantidadeCardsByPeriodo> getUserStoriesByPeriodoAndUser(Long projetoId, Long usuarioId) {
        Projeto projeto = projetoService.getById(projetoId);
        List<FatoUserStoryTemporais> resultados;
    
        if (usuarioId == null) {
            resultados = fatoUserStoryTemporaisRepository.findByProjeto(projeto);
        } else {
            Usuario usuario = usuarioService.getById(usuarioId);
            resultados = fatoUserStoryTemporaisRepository.findByProjetoAndUsuario(projeto, usuario);
        }
    
        // Verifica null primeiro
        if (resultados == null || resultados.isEmpty()) {
            List<ResponseQuantidadeCardsByPeriodo> retornoNenhumRegistro = new ArrayList<>();
            retornoNenhumRegistro.add(new ResponseQuantidadeCardsByPeriodo("Nenhum Registro Encontrado", 0, 0));
            return retornoNenhumRegistro;
        }
    
        return agruparResultadosPorPeriodo(resultados);
    }

    private List<ResponseQuantidadeCardsByPeriodo> agruparResultadosPorPeriodo(List<FatoUserStoryTemporais> resultados) {
        Map<String, Integer[]> temposMap = new HashMap<>();
        List<ResponseQuantidadeCardsByPeriodo> retorno = new ArrayList<>();

        for (FatoUserStoryTemporais fato : resultados) {
            String periodo = fato.getPeriodo().getNome();
            Integer[] valores = temposMap.getOrDefault(periodo, new Integer[]{0, 0});
            valores[0] += fato.getQuantidadeUserStoriesCriadas();
            valores[1] += fato.getQuantidadeUserStoriesFinalizadas();
            temposMap.put(periodo, valores);
        }

        for (Map.Entry<String, Integer[]> entry : temposMap.entrySet()) {
            retorno.add(new ResponseQuantidadeCardsByPeriodo(entry.getKey(), entry.getValue()[0], entry.getValue()[1]));
        }

        return retorno;
    }
}