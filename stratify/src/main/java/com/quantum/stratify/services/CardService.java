package com.quantum.stratify.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.quantum.stratify.repositories.CardRepository;
import com.quantum.stratify.repositories.UserStoryRepository;

@Service
public class CardService {

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private UserStoryRepository UserStoryRepository;

    public long getTotalCards() {
        return UserStoryRepository.count();
    }
}
