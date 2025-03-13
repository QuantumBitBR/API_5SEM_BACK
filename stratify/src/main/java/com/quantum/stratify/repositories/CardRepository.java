package com.quantum.stratify.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.quantum.stratify.entities.UserStory;

@Repository
public interface CardRepository extends JpaRepository<UserStory, Long>{

    
}
