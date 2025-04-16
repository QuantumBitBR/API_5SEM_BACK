package com.quantum.stratify.repositories;

import com.quantum.stratify.entities.Usuario;
import com.quantum.stratify.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByEmail(String email);
    @Query("SELECT u.role FROM Usuario u WHERE u.email = :email")
    Role findRoleByEmail(@Param("email") String email);
}