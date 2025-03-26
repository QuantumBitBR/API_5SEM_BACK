package com.quantum.stratify.repositories;

import com.quantum.stratify.entities.FatoEficienciaUserStory;
import com.quantum.stratify.entities.UserStory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FatoEficienciaUserStoryRepository extends JpaRepository<FatoEficienciaUserStory, Long> {
    FatoEficienciaUserStory findByUserStoryId(Long userStoryId);

}
