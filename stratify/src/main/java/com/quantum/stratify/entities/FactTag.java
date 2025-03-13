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
@Table(name = "fact_tag")
public class FactTag {
@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "tag_name")
    private String tagName;

    @Column(name = "amount_us_on_time")
    private Integer amountUsOnTime;

    @Column(name = "amount_us_late")
    private Integer amountUsLate;

    @Column(name = "amount_us_client_requirement")
    private Integer amountUsClientRequirement;

    @Column(name = "amount_us_team_requirement")
    private Integer amountUsTeamRequirement;
}
