package com.quantum.stratify.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.quantum.stratify.entities.FactTag;

@Repository
public interface TagRepository extends JpaRepository<FactTag, Long>{

}
