package com.quantum.stratify.repositories;

import com.quantum.stratify.entities.FatoEficienciaUserStory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FatoEficienciaUserStoryRepository extends JpaRepository<FatoEficienciaUserStory, Long> {

}
