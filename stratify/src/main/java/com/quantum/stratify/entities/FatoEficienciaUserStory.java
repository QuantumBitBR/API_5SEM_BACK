package com.quantum.stratify.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.Duration;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
//@Entity
@Table(name = "fato_progresso_user_stories")
public class FatoEficienciaUserStory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="id_usuario", nullable = false)
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user_story", nullable = false)
    private UserStory userStory;

    @Column(name = "tempo_medio")
    private Duration tempoMedio;
}
