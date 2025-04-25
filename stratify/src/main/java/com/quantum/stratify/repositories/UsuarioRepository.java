package com.quantum.stratify.repositories;

import com.quantum.stratify.entities.Usuario;
import com.quantum.stratify.enums.Role;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import com.quantum.stratify.web.dtos.UsuarioDTO;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByEmail(String email);
    @Query("SELECT u.role FROM Usuario u WHERE u.email = :email")
    Role findRoleByEmail(@Param("email") String email);

    @Query("""
            SELECT DISTINCT new com.quantum.stratify.web.dtos.UsuarioDTO(u.id, u.nome)
            FROM Usuario u
            LEFT JOIN u.projetos p
            WHERE (:idProjeto IS NULL OR p.id = :idProjeto)
            AND (:idGestor IS NULL OR u.gestor.id = :idGestor)
    """)
    List<UsuarioDTO> findUsuarioByProjetoAndGestor(
        @Param("idProjeto") Long idProjeto,
        @Param("idGestor") Long idGestor
    );
}
