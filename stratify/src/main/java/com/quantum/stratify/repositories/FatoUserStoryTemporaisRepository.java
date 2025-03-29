package com.quantum.stratify.repositories;

import com.quantum.stratify.entities.FatoUserStoryTemporais;
import com.quantum.stratify.entities.Periodo;
import com.quantum.stratify.entities.Projeto;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface FatoUserStoryTemporaisRepository extends JpaRepository<FatoUserStoryTemporais, Long> {

    List<FatoUserStoryTemporais> findByPeriodo(Periodo periodo);
    
    List<FatoUserStoryTemporais> findByProjetoAndPeriodo(Projeto projeto, Periodo periodo);

}