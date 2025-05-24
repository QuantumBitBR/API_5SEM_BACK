package com.quantum.stratify.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "dim_status_historico")
public class StatusHistorico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_user_story", nullable = false)
    private UserStory userStory;

    @Column(name = "data_retrabalho")
    private LocalDateTime dataRetrabalho;

    @ManyToOne
    @JoinColumn(name = "id_status", nullable = false)
    private Status status;
}