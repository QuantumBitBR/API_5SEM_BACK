package com.quantum.stratify.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.quantum.stratify.entities.FatoTagUserStory;
import com.quantum.stratify.entities.Projeto;

@Repository
public interface FatoProgressoRepository extends JpaRepository<FatoTagUserStory, Long> {

    List<FatoTagUserStory> findByProjeto(Projeto projetoId);

    @Query("SELECT COUNT(f) FROM FatoProgressoUserStory f")
    Long countTotalCards();
}
