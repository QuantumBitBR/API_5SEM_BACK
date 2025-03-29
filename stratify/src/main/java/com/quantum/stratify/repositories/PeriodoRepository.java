package com.quantum.stratify.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.quantum.stratify.entities.Periodo;

public interface PeriodoRepository extends JpaRepository<Periodo, Long> {

    List<Periodo> findByNomeContainingIgnoreCase(String termo);
}