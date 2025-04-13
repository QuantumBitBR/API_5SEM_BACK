package com.quantum.stratify.entities;

import java.util.List;

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

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "dim_usuario")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String email;

    private String senha;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = "is_enable", nullable = false)
    private Boolean isEnable;

    @ManyToOne
    @JoinColumn(name="id_gestor")
    private Usuario gestor;

    @OneToMany(mappedBy = "gestor")
    private List<Usuario> subordinado;

    
    @OneToMany(mappedBy = "usuario")
    private List<FatoEficienciaUserStory> eficienciaUserStories;

    @ManyToMany
    @JoinTable(name = "relacionamento_projeto_usuario",
    joinColumns = @JoinColumn(name = "id_usuario"),
    inverseJoinColumns = @JoinColumn(name = "id_projeto"))
    private List<Projeto> projetos;
}
