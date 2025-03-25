package com.quantum.stratify.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor


//OBS: Entidade sem objetivo de ser utilizada no momento. Caso necessário, retirar entity do comentario para voltar a funcionar
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

}
