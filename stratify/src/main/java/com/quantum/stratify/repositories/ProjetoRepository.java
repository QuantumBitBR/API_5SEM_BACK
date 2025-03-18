package com.quantum.stratify.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.quantum.stratify.entities.Projeto;

@Repository
public interface ProjetoRepository extends JpaRepository<Projeto, Long>{

}
