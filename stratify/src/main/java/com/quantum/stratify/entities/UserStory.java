package com.quantum.stratify.entities;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "dim_user_story")
public class UserStory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String assunto;

    @Column(name = "criado_em")
    private LocalDateTime criadoEm;

    @Column(name = "finalizado_em")
    private LocalDateTime finalizadoEm;

    private Boolean encerrado;

    private Boolean bloqueado;

    @Column(name = "data_limite")
    private LocalDateTime dataLimite;

    @OneToMany(mappedBy = "userStory")
    private List<FatoEficienciaUserStory> eficienciaUserStories;

    @Column(name = "id_taiga")
    private long idTaiga;

    @ManyToOne
    @JoinColumn(name = "id_status", nullable = false)
    private Status status;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "id_projeto", nullable = false)
    private Projeto projeto;

    @ManyToMany
    @JoinTable(name = "relacionamento_tag_user_story",
        joinColumns = @JoinColumn(name = "id_user_story"),
        inverseJoinColumns = @JoinColumn(name = "id_tag"))
    private List<Tag> tags;
}
