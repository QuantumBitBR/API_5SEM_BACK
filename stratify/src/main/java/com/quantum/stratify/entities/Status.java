package com.quantum.stratify.entities;

import com.quantum.stratify.enums.StatusTypes;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

//OBS: Entidade sem objetivo de ser utilizada no momento. Caso necessário, retirar entity do comentario para voltar a funcionar
@Entity
@Table(name = "dim_status")
public class Status {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "tipo")
    @Enumerated(EnumType.STRING)
    private StatusTypes type;
}
