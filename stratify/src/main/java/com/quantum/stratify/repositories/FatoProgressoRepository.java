package com.quantum.stratify.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.quantum.stratify.entities.FatoProgressoUserStory;
import com.quantum.stratify.entities.Projeto;

@Repository
public interface FatoProgressoRepository extends JpaRepository<FatoProgressoUserStory, Long> {

    List<FatoProgressoUserStory> findByProjeto(Projeto projetoId);

    @Query("SELECT SUM(f.quantidadeUserStories) FROM FatoProgressoUserStory f")
    Long countTotalCards();
}
