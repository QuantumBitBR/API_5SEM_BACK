package com.quantum.stratify.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.quantum.stratify.entities.FatoUserStoryTemporais;
import com.quantum.stratify.entities.Projeto;
import com.quantum.stratify.entities.Usuario;


@Repository
public interface FatoUserStoryTemporaisRepository extends JpaRepository<FatoUserStoryTemporais, Long> {

    List<FatoUserStoryTemporais> findByProjeto(Projeto periodo);
    
    @Query("SELECT f FROM FatoUserStoryTemporais f " +
           "WHERE f.projeto.id = :projetoId AND f.usuario.id = :usuarioId")
    List<FatoUserStoryTemporais> findByProjetoAndUsuario(Projeto projeto, Usuario usuario);

    @Query("SELECT CASE WHEN COUNT(f) > 0 THEN true ELSE false END " +
           "FROM FatoUserStoryTemporais f " +
           "WHERE f.usuario.id = :usuarioId")
    boolean existsByUsuarioAndProjeto(@Param("usuarioId") Long usuarioId);

}