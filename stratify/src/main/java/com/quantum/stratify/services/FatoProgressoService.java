package com.quantum.stratify.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.quantum.stratify.repositories.FatoProgressoRepository;

@Service
public class FatoProgressoService {
    @Autowired
    private FatoProgressoRepository fatoProgressoRepository;
}
