package com.quantum.stratify.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.quantum.stratify.entities.FatoEficienciaUserStory;
import com.quantum.stratify.web.dtos.TempoMedioPorProjetoDTO;

@Repository
public interface FatoEficienciaUserStoryRepository extends JpaRepository<FatoEficienciaUserStory, Long> {
    FatoEficienciaUserStory findByUserStoryId(Long userStoryId);

    @Query("SELECT new com.quantum.stratify.web.dtos.TempoMedioPorProjetoDTO( " +
            "us.id, us.assunto, fe.tempoMedio) " +
            "FROM FatoEficienciaUserStory fe " +
            "LEFT JOIN fe.userStory us " +
            "WHERE fe.projeto.id = :projetoId")
    List<TempoMedioPorProjetoDTO> findByProjetoId(@Param("projetoId") Long projetoId);

    @Query("SELECT new com.quantum.stratify.web.dtos.TempoMedioPorProjetoDTO( " +
            "us.id, us.assunto, fe.tempoMedio) " +
            "FROM FatoEficienciaUserStory fe " +
            "LEFT JOIN fe.userStory us " )
    List<TempoMedioPorProjetoDTO> getAll();

    @Query("SELECT new com.quantum.stratify.web.dtos.TempoMedioPorProjetoDTO( " +
            "us.id, us.assunto, fe.tempoMedio) " +
            "FROM FatoEficienciaUserStory fe " +
            "LEFT JOIN fe.userStory us " +
            "WHERE fe.projeto.id = :projetoId AND fe.usuario.id = :usuarioId")
    List<TempoMedioPorProjetoDTO> findByProjetoIdAndUsuarioId(@Param("projetoId") Long projetoId, @Param("usuarioId") Long usuarioId);

    @Query("SELECT new com.quantum.stratify.web.dtos.TempoMedioPorProjetoDTO( " +
            "us.id, us.assunto, fe.tempoMedio) " +
            "FROM FatoEficienciaUserStory fe " +
            "LEFT JOIN fe.userStory us " +
            "WHERE fe.usuario.id = :usuarioId")
    List<TempoMedioPorProjetoDTO> findByUsuarioId(@Param("usuarioId")Long usuarioId);

}