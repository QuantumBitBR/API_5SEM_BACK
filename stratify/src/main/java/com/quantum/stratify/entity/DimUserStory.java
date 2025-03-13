package com.quantum.stratify.entity;


import javax.persistence.*;
import java.util.Date;


@Entity
public class DimUserStory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_var")
    private DimVar dimVar;

    @ManyToOne
    @JoinColumn(name = "id_time")
    private DimTime dimTime;

    @ManyToOne
    @JoinColumn(name = "id_tag")
    private FactTag factTag;

    // Getters e Setters
}

// Defina outras entidades conforme necessário