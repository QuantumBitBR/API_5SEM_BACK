package com.quantum.stratify.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
public class DimTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Date dueDate;
    private Date finishedDate;
    private Date startDate;

    // Getters e Setters
}
