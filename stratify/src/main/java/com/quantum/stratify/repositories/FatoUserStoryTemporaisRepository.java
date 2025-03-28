package com.quantum.stratify.repositories;

import com.quantum.stratify.entities.FatoUserStoryTemporais;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface FatoUserStoryTemporaisRepository extends JpaRepository<FatoUserStoryTemporais, Long> {
}