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
import com.quantum.stratify.web.exceptions.EntityNotFoundException;


@Service
public class FatoUserStoryTemporaisService {

    @Autowired
    private FatoUserStoryTemporaisRepository fatoUserStoryTemporaisRepository;

    @Autowired
    private ProjetoService projetoService;

    @Autowired
    private UsuarioService usuarioService;

    public List<ResponseQuantidadeCardsByPeriodo> getUserStoriesByPeriodoAndUser(Long projetoId, Long usuarioId) {
        try {
            Projeto projeto = projetoService.getById(projetoId);
            
            // Se usuário foi especificado, verifica se existe
            Usuario usuario = null;
            if (usuarioId != null) {
                usuario = usuarioService.getById(usuarioId);
            }
            
            List<FatoUserStoryTemporais> resultados;
            
            if (usuarioId == null) {
                resultados = fatoUserStoryTemporaisRepository.findByProjeto(projeto);
            } else {
                resultados = fatoUserStoryTemporaisRepository.findByProjetoAndUsuario(projeto, usuario);
            }
            
            if (resultados == null || resultados.isEmpty()) {
                throw new EntityNotFoundException("Nenhum registro de User Stories encontrado para os parâmetros fornecidos");
            }
            
            return agruparResultadosPorPeriodo(resultados);
            
        } catch (Exception e) {
            // Se for uma EntityNotFoundException do projetoService ou usuarioService, relança
            if (e instanceof EntityNotFoundException) {
                throw e;
            }
            // Para outros erros, lança como EntityNotFoundException
            throw new EntityNotFoundException("Não foi possível recuperar os dados solicitados", e);
        }
    }

    List<ResponseQuantidadeCardsByPeriodo> agruparResultadosPorPeriodo(List<FatoUserStoryTemporais> resultados) {
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