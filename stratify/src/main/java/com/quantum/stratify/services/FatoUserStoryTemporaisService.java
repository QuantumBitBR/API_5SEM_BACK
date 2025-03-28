package com.quantum.stratify.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.quantum.stratify.repositories.FatoUserStoryTemporaisRepository;


@Service
public class FatoUserStoryTemporaisService {
    
    @Autowired
    private FatoUserStoryTemporaisRepository fatoUserStoryTemporaisRepository;
}