package com.quantum.stratify.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.quantum.stratify.entities.FatoStatusUserStory;

@Repository
public interface FatoStatusUserStoryRepository extends JpaRepository<FatoStatusUserStory, Long> {
    
    // Quando filtra por projeto específico
@Query("SELECT s.tipo, SUM(fsus.quantidadeUserStory * 1.0) / " +
       "(SELECT SUM(fsus2.quantidadeUserStory * 1.0) FROM FatoStatusUserStory fsus2 WHERE fsus2.projeto.id = :projetoId) * 100 " +
       "FROM FatoStatusUserStory fsus JOIN fsus.status s " +
       "WHERE fsus.projeto.id = :projetoId " +
       "GROUP BY s.tipo")
List<Object[]> findStatusPercentuais(@Param("projetoId") Long projetoId);

// Quando não filtra (todos os projetos)
@Query("SELECT s.tipo, SUM(fsus.quantidadeUserStory * 1.0) / " +
       "(SELECT SUM(fsus2.quantidadeUserStory * 1.0) FROM FatoStatusUserStory fsus2) * 100 " +
       "FROM FatoStatusUserStory fsus JOIN fsus.status s " +
       "GROUP BY s.tipo")
List<Object[]> findStatusPercentuaisAll();

}
