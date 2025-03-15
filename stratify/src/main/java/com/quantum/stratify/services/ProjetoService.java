package com.quantum.stratify.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.quantum.stratify.repositories.ProjetoRepository;

@Service
public class ProjetoService {
    @Autowired
    private ProjetoRepository projetoRepository;
}
