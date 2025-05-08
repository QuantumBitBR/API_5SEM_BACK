package com.quantum.stratify.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.quantum.stratify.entities.UserStory;
import com.quantum.stratify.web.dtos.QuantidadeCardsPorStatusDTO;
import com.quantum.stratify.web.dtos.QuantidadeCardsPorTagDTO;

@Repository
public interface UserStoryRepository extends JpaRepository<UserStory, Long> {

    @Query("SELECT COUNT(u) FROM UserStory u")
    Long countTotalUserStories();

    @Query("SELECT COUNT(u) FROM UserStory u WHERE u.projeto.id = :idProjeto")
    Long countByProject(@Param("idProjeto") Long idProjeto);

    @Query("SELECT COUNT(u) FROM UserStory u WHERE u.usuario.id = :idUsuario")
    Long countByUser(@Param("idUsuario") Long idUsuario);

    @Query("SELECT COUNT(u) FROM UserStory u WHERE u.projeto.id = :idProjeto AND u.usuario.id = :idUsuario")
    Long countByProjectAndUser(@Param("idProjeto") Long idProjeto, @Param("idUsuario") Long idUsuario);

    @Query("""
        SELECT new com.quantum.stratify.web.dtos.QuantidadeCardsPorTagDTO(
            t.nome,
            COUNT(us)
        )
        FROM UserStory us
        JOIN us.tags t
        WHERE (:projetoId IS NULL OR us.projeto.id = :projetoId)
        AND (:usuarioId IS NULL OR us.usuario.id = :usuarioId)
        GROUP BY t.id, t.nome   
    """)
    List<QuantidadeCardsPorTagDTO> contarUserStoriesPorTag(
        @Param("projetoId") Long projetoId,
        @Param("usuarioId") Long usuarioId
    );

    @Query("""
            SELECT new com.quantum.stratify.web.dtos.QuantidadeCardsPorStatusDTO(
                us.status.type,
                COUNT(us)
            )
            FROM UserStory us
            WHERE (:projetoId IS NULL OR us.projeto.id = :projetoId)
            AND (:usuarioId IS NULL OR us.usuario.id = :usuarioId)
            GROUP BY us.status.type
           """)
        List<QuantidadeCardsPorStatusDTO> contarUserStoriesPorStatus(
            @Param("projetoId") Long projetoId,
            @Param("usuarioId") Long usuarioId
        );
    
        @Query("SELECT s.nome, COUNT(us) FROM UserStory us " +
           "JOIN us.sprint s " +
           "WHERE us.projeto.id = :projetoId " +
           "GROUP BY s.nome")
        List<Object[]> countBySprintGrouped(@Param("projetoId") Long projetoId);
        
        @Query("SELECT s.nome, COUNT(us) FROM UserStory us " +
            "JOIN us.sprint s " +
            "WHERE us.projeto.id = :projetoId " +
            "AND us.usuario.id = :usuarioId " +
            "GROUP BY s.nome")
        List<Object[]> countBySprintGroupedAndUser(
            @Param("projetoId") Long projetoId, 
            @Param("usuarioId") Long usuarioId);
        

}
