package com.quantum.stratify.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.quantum.stratify.entities.Projeto;
import com.quantum.stratify.web.dtos.ProjetoDTO;

@Repository
public interface ProjetoRepository extends JpaRepository<Projeto, Long>{

    @Query("""
        SELECT new com.quantum.stratify.web.dtos.ProjetoDTO(p.id, p.nome)
        FROM Projeto p
        LEFT JOIN p.usuarios u
        WHERE (:idUsuario IS NULL OR u.id = :idUsuario)
        GROUP BY p.id, p.nome
        """)
    List<ProjetoDTO> findProjetoByUsuarioId(@Param("idUsuario") Long idUsuario);

}
