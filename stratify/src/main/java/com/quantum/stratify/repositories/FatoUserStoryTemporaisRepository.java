package com.quantum.stratify.repositories;

import com.quantum.stratify.entities.FatoUserStoryTemporais;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface FatoUserStoryTemporaisRepository extends JpaRepository<FatoUserStoryTemporais, Long> {

    @Query("SELECT f FROM FatoUserStoryTemporais f " +
           "JOIN f.periodo p " +
           "WHERE p.nome BETWEEN :startDate AND :endDate " +
           "AND f.projeto.id = :projetoId")
    List<FatoUserStoryTemporais> findByPeriodoAndProjeto(
            @Param("startDate") String startDate,
            @Param("endDate") String endDate,
            @Param("projetoId") Long projetoId);
}