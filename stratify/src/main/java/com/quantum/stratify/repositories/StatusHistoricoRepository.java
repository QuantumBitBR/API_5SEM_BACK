package com.quantum.stratify.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.quantum.stratify.entities.StatusHistorico;

import java.util.List;

@Repository
public interface StatusHistoricoRepository extends JpaRepository<StatusHistorico, Long> {
    
}

