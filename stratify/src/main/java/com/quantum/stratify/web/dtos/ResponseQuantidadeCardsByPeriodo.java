package com.quantum.stratify.web.dtos;

import java.time.LocalDate;

public class ResponseQuantidadeCardsByPeriodo { LocalDate startDate; LocalDate endDate; Long totalCardsCreated; Long totalCardsFinished;

    public ResponseQuantidadeCardsByPeriodo() {}

    public ResponseQuantidadeCardsByPeriodo(LocalDate startDate, LocalDate endDate, Long totalCardsCreated, Long totalCardsFinished) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.totalCardsCreated = totalCardsCreated;
        this.totalCardsFinished = totalCardsFinished;
    }
}