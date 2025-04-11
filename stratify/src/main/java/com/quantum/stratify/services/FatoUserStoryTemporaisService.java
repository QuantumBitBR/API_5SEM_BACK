package com.quantum.stratify.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.quantum.stratify.entities.FatoUserStoryTemporais;
import com.quantum.stratify.entities.Usuario;
import com.quantum.stratify.repositories.FatoUserStoryTemporaisRepository;
import com.quantum.stratify.web.dtos.ResponseQuantidadeCardsByPeriodo;

import jakarta.transaction.Transactional;


@Service
public class FatoUserStoryTemporaisService {
    
    @Autowired
    private FatoUserStoryTemporaisRepository fatoUserStoryTemporaisRepository;

    @Autowired
    private ProjetoService projetoService;
    
    @Autowired
    private UsuarioService usuarioService;

    @Transactional
    public List<ResponseQuantidadeCardsByPeriodo> getUserStoriesByPeriodoAndUser(Long projetoId, Long usuarioId) {
        List<FatoUserStoryTemporais> resultados = new ArrayList<>();

        List<ResponseQuantidadeCardsByPeriodo> retorno = new ArrayList<>();
        
        if (projetoId != null && usuarioId == null) {
            resultados = fatoUserStoryTemporaisRepository.findByProjeto(projetoService.getById(projetoId));

            Map<String, Integer[]> temposMap = new HashMap<>();
        
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

        } else if (usuarioId != null) {
            boolean usuarioTemRegistros = fatoUserStoryTemporaisRepository.existsByUsuarioAndProjeto(usuarioId);
            if (!usuarioTemRegistros) {;
                retorno.add(new ResponseQuantidadeCardsByPeriodo("Nenhum registro encontrado", 0, 0));
            }else{
                Usuario usuario = usuarioService.getById(usuarioId);
                resultados = fatoUserStoryTemporaisRepository.findByProjetoAndUsuario(
                    projetoService.getById(projetoId), 
                    usuario);

                    Map<String, Integer[]> temposMap = new HashMap<>();
        
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
                    
            }
        }

        return retorno;
        
    }
    
}