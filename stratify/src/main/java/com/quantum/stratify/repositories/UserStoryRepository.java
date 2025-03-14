package com.quantum.stratify.repositories;

import com.quantum.stratify.entities.UserStory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserStoryRepository extends JpaRepository<UserStory, Integer> {

    // Método para contar o total de cards (user stories)
    long count();
}