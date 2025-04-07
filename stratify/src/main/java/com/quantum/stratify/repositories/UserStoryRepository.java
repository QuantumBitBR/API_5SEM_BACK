package com.quantum.stratify.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.quantum.stratify.entities.UserStory;

@Repository
public interface UserStoryRepository extends JpaRepository<UserStory, Long> {

    @Query("SELECT COUNT(u) FROM UserStory u")
    Long countTotalUserStories();

    @Query("SELECT COUNT(u) FROM UserStory u WHERE u.projeto.id = :idProjeto")
    Long countByProject(@Param("idProjeto") Long idProjeto);

    @Query("SELECT COUNT(u) FROM UserStory u WHERE u.usuario.id = :idUsuario")
    Long countByUser(@Param("idUsuario") Long idUsuario);

    @Query("SELECT COUNT(u) FROM UserStory u WHERE u.projeto.id = :idProjeto AND u.usuario.id = :idUsuario")
    Long countByProjectAndUser(@Param("idProjeto") Long idProjeto, @Param("idUsuario") Long idUsuario);
}