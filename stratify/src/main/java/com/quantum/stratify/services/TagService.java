package com.quantum.stratify.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.quantum.stratify.repositories.TagRepository;

@Service
public class TagService {

    @Autowired
    private TagRepository tagRepository;
}
