package com.quantum.stratify.repositories;

import com.quantum.stratify.entities.UserStory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;

@Repository
public interface UserStoryRepository extends JpaRepository<UserStory, Long> {
    
    @Query("SELECT COUNT(us) FROM UserStory us WHERE us.criadoEm BETWEEN :dataInicio AND :dataFim")
    Long countCriadasNoPeriodo(@Param("dataInicio") LocalDateTime dataInicio, 
                             @Param("dataFim") LocalDateTime dataFim);
    
    @Query("SELECT COUNT(us) FROM UserStory us WHERE us.finalizadoEm BETWEEN :dataInicio AND :dataFim AND us.encerrado = true")
    Long countFinalizadasNoPeriodo(@Param("dataInicio") LocalDateTime dataInicio, 
                                  @Param("dataFim") LocalDateTime dataFim);
}