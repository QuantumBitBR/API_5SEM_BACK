package com.quantum.stratify.repository;

import java.util.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.quantum.stratify.entity.*;

public interface DimTimeRepository extends JpaRepository<DimTime, Integer> {
    @Query("SELECT COUNT(d) FROM DimUserStory d WHERE d.dimTime.startDate BETWEEN :startDate AND :endDate")
    Long countCardsBetweenDates(@Param("startDate") Date startDate, @Param("endDate") Date endDate);
}