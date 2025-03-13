package com.quantum.stratify.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;
import com.quantum.stratify.repository.DimTimeRepository;

@Service
public class CardService {

    @Autowired
    private DimTimeRepository dimTimeRepository;

    public Long getTotalCardsBetweenDates(Date startDate, Date endDate) {
        return dimTimeRepository.countCardsBetweenDates(startDate, endDate);
    }
}