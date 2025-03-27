package com.quantum.stratify.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "fato_user_story_temporais")
public class FatoUserStoryTemporais {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "quantidade_user_stories_criadas", nullable = false)
    private Integer quantidadeUserStoriesCriadas;

    @Column(name = "quantidade_user_stories_finalizadas", nullable = false)
    private Integer quantidadeUserStoriesFinalizadas;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_periodo", nullable = false)
    private Periodo periodo;

    // Relacionamento com Projeto
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_projeto", nullable = false)
    private Projeto projeto;
}