package com.quantum.stratify.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.quantum.stratify.entities.UserStory;

@Repository
public interface UserStoryRepository extends JpaRepository<UserStory, Long> {
    
    @Query("SELECT COUNT(u) FROM UserStory u")
    Long countTotalUserStories();
}