package com.quantum.stratify.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.quantum.stratify.repositories.CardRepository;

@Service
public class CardService {

    @Autowired
    private CardRepository cardRepository;
}
