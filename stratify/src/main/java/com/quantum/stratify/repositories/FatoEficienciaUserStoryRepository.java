package com.quantum.stratify.repositories;

import com.quantum.stratify.entities.FatoEficienciaUserStory;
import com.quantum.stratify.entities.UserStory;
import com.quantum.stratify.web.dtos.TempoMedioPorProjetoDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FatoEficienciaUserStoryRepository extends JpaRepository<FatoEficienciaUserStory, Long> {
    FatoEficienciaUserStory findByUserStoryId(Long userStoryId);

    @Query("SELECT new com.quantum.stratify.web.dtos.TempoMedioPorProjetoDTO( " +
            "us.id, us.assunto, fe.tempoMedio) " +
            "FROM FatoEficienciaUserStory fe " +
            "LEFT JOIN fe.userStory us " +
            "WHERE fe.projeto.id = :projetoId")
    List<TempoMedioPorProjetoDTO> findByProjetoId(@Param("projetoId") Long projetoId);

}