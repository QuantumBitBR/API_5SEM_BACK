package com.quantum.stratify.entities;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

import com.quantum.stratify.enums.Role;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "dim_usuario")
public class Usuario implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String email;

    private String senha;

    @Enumerated(EnumType.STRING)
    private Role role;



    @Getter
    @Column(name = "require_reset")
    private boolean requireReset;


    @Column(name = "is_enable", nullable = false)
    private Boolean isEnable;

    @ManyToOne
    @JoinColumn(name="id_gestor")
    private Usuario gestor;

    @OneToMany(mappedBy = "gestor")
    private List<Usuario> subordinado;



    @OneToMany(mappedBy = "usuario")
    private List<FatoEficienciaUserStory> eficienciaUserStories;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (this.role == Role.ADMIN) {
            return List.of(new SimpleGrantedAuthority("ADMIN"));
        } else if (this.role == Role.USER) {
            return List.of(new SimpleGrantedAuthority("USER"));
        } else if (this.role == Role.GESTOR) {
            return List.of(new SimpleGrantedAuthority("GESTOR"));
        } else {
            throw new IllegalArgumentException("Role inválido para o usuário.");
        }
    }

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.isEnable;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Usuario usuario = (Usuario) o;
        return Objects.equals(id, usuario.id);
    }

    @ManyToMany
    @JoinTable(name = "relacionamento_projeto_usuario",
    joinColumns = @JoinColumn(name = "id_usuario"),
    inverseJoinColumns = @JoinColumn(name = "id_projeto"))
    private List<Projeto> projetos;
}
