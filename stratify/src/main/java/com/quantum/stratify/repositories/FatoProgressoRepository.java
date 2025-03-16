package com.quantum.stratify.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.quantum.stratify.entities.FatoProgressoUserStory;

@Repository
public interface FatoProgressoRepository extends JpaRepository<FatoProgressoUserStory, Long> {

}
