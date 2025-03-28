package com.quantum.stratify.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.quantum.stratify.services.FatoUserStoryTemporaisService;

@RestController
public class FatoUserStoryTemporiasController {
    
    @Autowired
    private FatoUserStoryTemporaisService service;
}