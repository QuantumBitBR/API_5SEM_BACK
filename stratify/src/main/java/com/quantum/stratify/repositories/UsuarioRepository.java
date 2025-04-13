package com.quantum.stratify.repositories;

import com.quantum.stratify.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UserDetails;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    @Query("SELECT u FROM Usuario u WHERE u.email = :email AND u.senha = :senha")
    UserDetails loadUserByEmail(@Param("email") String email, @Param("senha") String senha);

    Usuario findByEmail(String email);
}