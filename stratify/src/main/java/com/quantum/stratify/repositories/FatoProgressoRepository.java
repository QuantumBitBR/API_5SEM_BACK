package com.quantum.stratify.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.quantum.stratify.entities.FatoProgressoUserStory;
import com.quantum.stratify.entities.Projeto;

@Repository
public interface FatoProgressoRepository extends JpaRepository<FatoProgressoUserStory, Long> {

    List<FatoProgressoUserStory> findByProjeto(Projeto projetoId);

}
