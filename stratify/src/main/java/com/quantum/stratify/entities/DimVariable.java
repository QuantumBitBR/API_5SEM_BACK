package com.quantum.stratify.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name = "dim_variable")
public class DimVariable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "is_blocked")
    private Boolean isBlocked;

    @Column(name = "team_requirement")
    private Boolean teamRequirement;

    @Column(name = "client_requirement")
    private Boolean clientRequirement;

    @Column(name = "is_iocaine")
    private Boolean isIocaine;

    @Column(name = "is_closed")
    private Boolean isClosed;
}
