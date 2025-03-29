package com.quantum.stratify.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.quantum.stratify.entities.FatoStatusUserStory;
import com.quantum.stratify.entities.Projeto;

@Repository
public interface FatoStatusUserStoryRepository extends JpaRepository<FatoStatusUserStory, Long> {
    List<FatoStatusUserStory> findByProjeto(Projeto projeto);

}
